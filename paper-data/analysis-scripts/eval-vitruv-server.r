library(tseries)
library(rcompanion)
library(jsonlite)
library(stats)
source("location-and-scales.r")
source("non-parametric-cis.r")
source("normal-eval-functions.r")
source("generic-eval.r")

out <- evaluateMeasurements("raw-data.csv")
write_json(out$results, "eval-results.json", pretty = TRUE)

pdf(file="boxplots.pdf")
configs <- c("-10-5", "-100-10", "-300-20")
communication <- c("worker", "controller", "worker-to-controller", "controller-to-worker")
g1 <- c("local-local-", "server-original-client-original-", "server-security2-proxy-client-security-jetty-http11-")
g2 <- c("server-original-client-original-", "server-jetty-client-jetty-http11-", "server-jetty-client-jetty-http2-")
g3 <- c("server-original-client-original-", "server-security2-direct-client-security-jetty-http11-", "server-security2-proxy-client-security-jetty-http11-")
g4 <- c("server-original-client-original-", "server-security2-direct-client-security-jetty-http2-", "server-security2-proxy-client-security-jetty-http2-")
g5 <- c("server-security2-direct-client-security-jetty-http11-", "server-security2-proxy-client-security-jetty-http11-", "server-security2-direct-client-security-jetty-http2-", "server-security2-proxy-client-security-jetty-http2-")
groups <- list(g1=g1,g2=g2,g3=g3,g4=g4,g5=g5)
for (gName in names(groups)) {
  serverClientConfig <- groups[[gName]]
  for (conf in configs) {
    for (com in communication) {
      bpData <- c()
      for (scc in serverClientConfig) {
        id <- paste(scc, com, conf, sep = "")
        if (!is.null(out$allSeries[[id]])) {
          bpData[[id]] <- out$allSeries[[id]]
        }
      }
      boxplot(bpData)
    }
  }
}
dev.off()
