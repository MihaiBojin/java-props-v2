# props: A library for reading layered application settings in Java

[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=props-sh_props&metric=bugs)](https://sonarcloud.io/summary/new_code?id=props-sh_props)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=props-sh_props&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=props-sh_props)
[![codecov](https://codecov.io/gh/props-sh/props/branch/main/graph/badge.svg?token=RMUOVHFHKG)](https://codecov.io/gh/props-sh/props)
[![javadoc](https://javadoc.io/badge2/sh.props/props-core/javadoc.svg)](https://javadoc.io/doc/sh.props/props-core)

## >>> This project is now archived. No further work is planned. <<<


This library manages properties and secrets from multiple sources, giving users a unified API for
loading type safe system, environment, classpath and file based properties while also deciding which
source takes precedence and how often the values should be refreshed.

The repository hosts a number of projects:

- `props-core`: the core logic of the project, has not third-party dependencies and it is
  self-contained
- `props-aws`: a `Source` backed by `AWS Secrets Manager`
- `props-mongodb`: a `Source` backed by a MongoDB database collection

# Features

- Access values once or repeatedly
- Fast reads (values are cached until changed)
- Events triggered when values change
- Small core module with no third-party dependencies
- Built with thread-safety in mind
- Deterministic source precedence

# Contributing to the project

Please see the [contributor guide](./CONTRIBUTING.md).

# License

Copyright (c) 2021-2022 Mihai Bojin

Licensed under the MIT License

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
associated documentation files (the "Software"), to deal in the Software without restriction,
including without limitation the rights to use, copy, modify, merge, publish, distribute,
sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial
portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES
OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
