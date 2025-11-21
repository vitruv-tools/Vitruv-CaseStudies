calculateMeanAndSd <- function(x, out = c()) {
  if (is.null(out$mean)) {
    out$mean <- mean(x)
  }
  if (is.null(out$sd)) {
    out$sd <- sd(x)
  }
  out
}

calculateCIBound <- function(out, z, factor) {
  out$mean + factor * z * out$sd / sqrt(out$n)
}

calculateNormalConfidenceIntervals <- function(x, out = c(), level = 0.95) {
  calculateMeanAndSd(x, out)
  z = qnorm((1 + level) / 2)
  out$normLowerBound <- calculateCIBound(out, z, -1)
  out$normUpperBound <- calculateCIBound(out, z, 1)
  out
}

normalNumberOfRequiredMeasurements <- function(x, out = c()) {
  calculateMeanAndSd(x, out)
  y$numberMeasurements <- (100 * 1.96 * out$sd / (1 * out$mean))^2
  out
}
