FROM mcr.microsoft.com/devcontainers/cpp:1-ubuntu-22.04

ARG REINSTALL_CMAKE_VERSION_FROM_SOURCE="none"

# Optionally install the cmake for vcpkg
COPY ./reinstall-cmake.sh /tmp/

RUN if [ "${REINSTALL_CMAKE_VERSION_FROM_SOURCE}" != "none" ]; then \
        chmod +x /tmp/reinstall-cmake.sh && /tmp/reinstall-cmake.sh ${REINSTALL_CMAKE_VERSION_FROM_SOURCE}; \
    fi \
    && rm -f /tmp/reinstall-cmake.sh


# updata & install Java
RUN apt-get update && apt-get install -y \
    build-essential \
    openjdk-11-jdk \
    && apt-get clean

# Install ANTLR
COPY ./setup-antlr.sh /tmp/
RUN chmod +x /tmp/setup-antlr.sh && /tmp/setup-antlr.sh && rm -f /tmp/setup-antlr.sh antlr-4.9.3-complete.jar

# Install antlr4-runtime
COPY ./antlr4-runtime_4.13.1_amd64.deb /tmp/
RUN apt-get install -y /tmp/antlr4-runtime_4.13.1_amd64.deb && rm -f /tmp/antlr4-runtime_4.13.1_amd64.deb

# [Optional] Uncomment this section to install additional vcpkg ports.
# RUN su vscode -c "${VCPKG_ROOT}/vcpkg install <your-port-name-here>"

