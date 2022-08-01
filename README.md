# Vitruv Applications for Component-Based Systems
[![GitHub Action CI](https://github.com/vitruv-tools/Vitruv-Applications-ComponentBasedSystems/workflows/CI/badge.svg)](https://github.com/vitruv-tools/Vitruv-Applications-ComponentBasedSystems/actions?query=workflow%3ACI)
[![Issues](https://img.shields.io/github/issues/vitruv-tools/Vitruv-Applications-ComponentBasedSystems.svg)](https://github.com/vitruv-tools/Vitruv-Applications-ComponentBasedSystems/issues)
[![License](https://img.shields.io/github/license/vitruv-tools/Vitruv-Applications-ComponentBasedSystems.svg)](https://raw.githubusercontent.com/vitruv-tools/Vitruv-Applications-ComponentBasedSystems/main/LICENSE)

[Vitruv](https://vitruv.tools) is a framework for view-based software development. It assumes different models to be used for describing a software system,
which are automatically kept consistent by the framework and its applications. For general information on Vitruv, see our [GitHub Organisation](https://github.com/vitruv-tools) and our [Wiki](https://github.com/vitruv-tools/.github/wiki).

This project contains an application of the Vitruv framework with case studies in the domain of component-based systems engineering. It provides functionality for creating views on Java code, UML models and PCM models ([Palladio component models](https://github.com/palladiosimulator)) and for preserving consistency between such models.

## Installation

Vitruv can be installed in Eclipse via the [nightly update site](https://vitruv.tools/updatesite/nightly). A wiki page provides [detailed instructions for using or extending Vitruv](https://github.com/vitruv-tools/.github/wiki/Getting-Started).

## Project Development

The Vitruv applications are realized as Eclipse Plug-ins and depend on the following Eclipse tools:
- Eclipse Modeling Framework (EMF) _as the modelling environment_
- Xtext _for using the consistency specification languages_
- Xtend _for code_
