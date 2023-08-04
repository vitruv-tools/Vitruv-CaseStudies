# Vitruv Case Studies
[![GitHub Action CI](https://github.com/vitruv-tools/Vitruv-CaseStudies/actions/workflows/ci.yml/badge.svg)](https://github.com/vitruv-tools/Vitruv-CaseStudies/actions/workflows/ci.yml)
[![Latest Release](https://img.shields.io/github/release/vitruv-tools/Vitruv-CaseStudies.svg)](https://github.com/vitruv-tools/Vitruv-CaseStudies/releases/latest)
[![Issues](https://img.shields.io/github/issues/vitruv-tools/Vitruv-CaseStudies.svg)](https://github.com/vitruv-tools/Vitruv-CaseStudies/issues)
[![License](https://img.shields.io/github/license/vitruv-tools/Vitruv-CaseStudies.svg)](https://raw.githubusercontent.com/vitruv-tools/Vitruv-CaseStudies/main/LICENSE)

[Vitruvius](https://vitruv.tools) is a framework for view-based software development.
It assumes different models to be used for describing a software system, which are automatically kept consistent by the framework executing (semi-)automated rules that preserve consistency.
These models are modified only via views, which are projections from the underlying models.
For general information on Vitruvius, see our [GitHub Organisation](https://github.com/vitruv-tools) and our [Wiki](https://github.com/vitruv-tools/.github/wiki).

This project contains case studies for the [Vitruvius framework](https://github.com/vitruv-tools/Vitruv), in particular an example application of Vitruvius in the domain of component-based systems engineering.
It provides functionality for creating views on Java code, UML models and PCM models ([Palladio Component Model](https://github.com/palladiosimulator)) and for preserving consistency between these models by providing `ChangePropagationSpecifications`.
In addition, it provides test cases for demo applications based on the simple [families, persons and insurance metamodels](https://github.com/kit-sdq/DemoMetamodels) defined in the [Vitruv-DSLs](https://github.com/vitruv-tools/Vitruv-DSLs) repository.

## Installation

Vitruvius can be installed in Eclipse via the [nightly update site](https://vitruv.tools/updatesite/nightly). A wiki page provides [detailed instructions for using or extending Vitruvius or parts of it](https://github.com/vitruv-tools/.github/wiki/Getting-Started).

## Project Development

Vitruvius, as well as the case studies, is realized as Eclipse plug-ins and depends on the following Eclipse tools:
- Eclipse Modeling Framework (EMF) _as the modelling environment_
- Xtext _for using the consistency specification languages_
- Xtend _for code_

This project depends on the following other Vitruvius projects:
- [Vitruv](https://github.com/vitruv-tools/Vitruv)
- [Vitruv-DSLs](https://github.com/vitruv-tools/Vitruv-DSLs)
