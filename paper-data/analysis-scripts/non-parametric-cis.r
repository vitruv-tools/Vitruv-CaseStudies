calculateNonParametricConfidenceInterval <- function(x, y = c(), level = 0.95, quantileLevel = 0.5, quantileValue = NULL) {
  if (is.null(quantileValue)) {
    quantileValue <- quantile(x, quantileLevel)
    q <- paste("q", quantileLevel * 100)
    y[[q]] <- quantileValue
  }
  z <- qnorm((1 + level) / 2)
  
  lowerIdx <- floor(y$n * quantileLevel - z * sqrt(y$n * quantileLevel * (1 - quantileLevel)))
  upperIdx <- 1 + ceiling(y$n * quantileLevel + z * sqrt(y$n * quantileLevel * (1 - quantileLevel)))

  sortedSeries <- sort(x)
  y$lowerBound <- sortedSeries[[lowerIdx]]
  y$upperBound <- sortedSeries[[upperIdx]]

  y$lowerError <- (quantileValue - y$lowerBound) / quantileValue
  y$upperError <- (y$upperBound - quantileValue) / quantileValue
  y
}

confirm <- function(x, sampleRepetitions = 200) {
  ciBounds <- list()
  
  for (subSampleSize in 10:length(x)) {
    print(subSampleSize)
    lowerBounds <- c()
    upperBounds <- c()
    medians <- c()
    
    for (iteration in 1:sampleRepetitions) {
      subsample <- sample(x, subSampleSize)
      nonCI <- calculateNonParametricConfidenceInterval(subsample)
      lowerBounds <- c(lowerBounds, nonCI$lowerBound)
      upperBounds <- c(upperBounds, nonCI$upperBound)
      medians <- c(medians, nonCI$p)
    }
    
    result <- c()
    result$size <- subSampleSize
    result$avgLowerBound <- mean(lowerBounds)
    result$avgUpperBound <- mean(upperBounds)
    result$avgMedian <- mean(medians)
    result$uppErrorBound <- result$avgMedian + 0.01 * result$avgMedian
    result$lowErrorBound <- result$avgMedian - 0.01 * result$avgMedian
    roundName <- paste("Round", subSampleSize, sep = "")
    ciBounds[[roundName]] = result
    
    if (result$avgLowerBound > result$lowErrorBound
        && result$avgUpperBound < result$uppErrorBound
        && is.null(ciBounds$n)) {
      ciBounds$n = subSampleSize
    }
  }
  ciBounds
}

plotConfirmResults <- function(x) {
  a <- c()
  for (column in names(x)) {
    val <- x[[column]]
    if (is.numeric(val)) {
      a$n <- val
    } else {
      a$x <- c(a$x, val$size)
      a$m <- c(a$m, val$avgMedian)
      a$e1 <- c(a$e1, val$uppErrorBound)
      a$e2 <- c(a$e2, val$lowErrorBound)
      a$c1 <- c(a$c1, val$avgUpperBound)
      a$c2 <- c(a$c2, val$avgLowerBound)
    }
  }
  
  plot(x=a$x, y=a$m, type="l", ylim = c(min(a$c2), max(a$c1)))
  lines(a$x, a$e1, col="red")
  lines(a$x, a$e2, col="red")
  lines(a$x, a$c1, col="blue")
  lines(a$x, a$c2, col="blue")
  abline(v=a$n, col="green")
}
