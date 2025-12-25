#!/bin/bash

set -e

patch -d boringssl -p1 < patches/boringssl/0001-add-aes-ige-mode.patch
patch -d boringssl -p1 < patches/boringssl/0001-only-build-what-we-need.patch
cd boringssl
python3 << EOF
import re

def remove_set_blocks(filename, varnames):
    with open(filename, "r", encoding="utf-8") as f:
        content = f.read()

    var_alternation = "|".join(re.escape(v) for v in varnames)

    pattern = rf"set\s*\(\s*(?:{var_alternation})\b[\s\S]*?\)"

    new_content = re.sub(pattern, "", content, flags=re.MULTILINE)

    with open(filename, "w", encoding="utf-8") as f:
        f.write(new_content)


if __name__ == "__main__":
    vars_to_remove = [
        "BSSL_SOURCES",
        "BSSL_INTERNAL_HEADERS",
        "CRYPTO_TEST_SOURCES",
        "CRYPTO_TEST_DATA",
        "DECREPIT_SOURCES",
        "DECREPIT_INTERNAL_HEADERS",
        "DECREPIT_TEST_SOURCES",
        "PKI_SOURCES",
        "PKI_HEADERS",
        "PKI_INTERNAL_HEADERS",
        "PKI_TEST_SOURCES",
        "PKI_TEST_DATA",
        "RUST_BSSL_CRYPTO_SOURCES",
        "RUST_BSSL_SYS_SOURCES",
        "SSL_TEST_SOURCES",
        "TEST_SUPPORT_SOURCES",
        "TEST_SUPPORT_INTERNAL_HEADERS",
        "TEST_SUPPORT_SOURCES_ASM",
        "TEST_SUPPORT_SOURCES_NASM",
        "URANDOM_TEST_SOURCES",
    ]
    remove_set_blocks("gen/sources.cmake", vars_to_remove)
EOF
