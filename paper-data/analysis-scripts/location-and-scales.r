calculateLocationAndScales <- function(x, out = c()) {
  out$n <- length(x)
  
  # Location parameters
  out$p25 <- quantile(x, 0.25, names = FALSE)
  out$median <- median(x)
  out$p75 <- quantile(x, 0.75, names = FALSE)
  out$p99 <- quantile(x, 0.99, names = FALSE)

  # Scale parameters
  out$iqr <- IQR(x)
  out$mad <- mad(x)
  out$qcd <- (out$p75 - out$p25) / (out$p75 + out$p25)
  out
}
