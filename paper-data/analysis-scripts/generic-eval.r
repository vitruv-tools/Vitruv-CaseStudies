library(tseries)
library(rcompanion)
library(stats)
source("location-and-scales.r")
source("non-parametric-cis.r")
source("normal-eval-functions.r")

evaluateSingleMeasurementSeries <- function(series, name) {
  out <- c()
  out$name <- name

  # Plots
  pdf(file=paste(name, "-line.pdf", sep=""))
  plot(series, type="l")
  title(sub=name)
  dev.off()
  pdf(file=paste(name, "-scatter.pdf", sep=""))
  plot(series)
  title(sub=name)
  dev.off()
  pdf(file=paste(name, "-box.pdf", sep=""))
  boxplot(series)
  title(sub=name)
  dev.off()
  pdf(file=paste(name, "-hist.pdf", sep=""))
  hist(series)
  title(sub=name)
  dev.off()
  pdf(file=paste(name, "-density.pdf", sep=""))
  plot(density(series))
  title(sub=name)
  dev.off()
  pdf(file=paste(name, "-ecdf.pdf", sep=""))
  plot.ecdf(series)
  title(sub=name)
  dev.off()
  
  # Autocorrelation (including for iid)
  pdf(file=paste(name, "-acf.pdf", sep=""))
  acfResult <- acf(series)
  dev.off()
  pdf(file=paste(name, "-lag.pdf", sep=""))
  lag.plot(ts(series), lags=4)
  title(sub=name)
  dev.off()
  
  # Test for stationarity
  stationarityTestResult <- adf.test(series)
  out$stationarityTestResult <- c()
  out$stationarityTestResult$pValue <- stationarityTestResult$p.value
  out$stationarityTestResult$statistic <- stationarityTestResult$statistic
  out$stationarityTestResult$parameter <- stationarityTestResult$parameter
  out$stationarityTestResult$method <- stationarityTestResult$method
  
  stationarityP <- stationarityTestResult$p.value
  if (stationarityP > 0.01) {
    print("non-stat")
    out$stationarity <- FALSE
    kpssTestResult <- kpss.test(series)
    out$kpss <- kpssTestResult$p.value
    return(out)
  }
  
  isIid <- TRUE
  crh <- 2 / sqrt(length(series))
  for (lag in 2:length(acfResult$acf)) {
    if (abs(acfResult$acf[[lag]]) > crh) {
      isIid <- FALSE
    }
  }
  out$iid <- isIid
  if (!isIid) {
    print("non-iid")
    return(out)
  }
  
  out <- calculateLocationAndScales(series, out)

  # Shapiro-Wilk test for normality and QQ plot
  normalTestResult <- shapiro.test(series)
  out$normalTestResult <- c()
  out$normalTestResult$pValue <- normalTestResult$p.value
  out$normalTestResult$statistic <- normalTestResult$statistic
  out$normalTestResult$method <- normalTestResult$method
  pdf(file=paste(name, "-qqnorm.pdf", sep=""))
  qqnorm(series, title=name)
  qqline(series)
  title(sub=name)
  dev.off()

  normalP <- out$normalTestResult$pValue
  if (normalP < 0.01) {
    # Non-parametric confidence interval for median
    out <- calculateNonParametricConfidenceInterval(series, out, quantileValue = out$median)
    #out$confirmResults <- confirm(series)
    #plotConfirmResults(out$confirmResults)
  } else {
    out <- calculateNormalConfidenceIntervals(series, out)
    #out <- normalNumberOfRequiredMeasurements(series, out)
  }
}

evaluateMeasurements <- function(file) {
  rawData <- read.csv(file, sep = ",", header = FALSE, fill = TRUE)
  results <- c()
  allSeries <- c()
  allData <- c()
  
  cIdx <- 1
  for (cIdx in 1:length(rawData[[1]])) {
    columnName <- rawData[[1]][[cIdx]]
    print(columnName)
    
    series <- c()
    for (pointIdx in 1:length(rawData)) {
      point <- rawData[[pointIdx]][[cIdx]]
      if (!is.na(point) && is.numeric(point)) {
        series <- c(series, point)
      }
    }
    allSeries[[columnName]] <- series
    allData <- c(allData, series)
    results[[columnName]] <- evaluateSingleMeasurementSeries(series, columnName)
  }
  
  pdf(file="all-scatter.pdf")
  plot(allData)
  dev.off()
  pdf(file="all-line.pdf")
  plot(allData,type="l")
  dev.off()
  pdf(file="all-in-one.pdf")
  plot(c(), c(), xlim=c(1, 1000), ylim=c(min(allData) - 100, max(allData) + 100), xlab="# Measurements", ylab="Execution Time (ms)")
  for (sName in names(allSeries)) {
    points(allSeries[[sName]])
  }
  dev.off()
  
  out <- list(results=results,allSeries=allSeries)
  out
}
