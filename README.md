# OpenBeans

OpenBeans is an IDE distribution. It packages the best there is in the Apache NetBeans* ecosystem, polishes it, fixes minor annoyances and serves them to you in a clean bundle. Formerly CoolBeans.

To download OpenBeans please head over to [www.openbeans.org](http://www.openbeans.org) or to the GitHub [releases page](https://github.com/OpenBeans/OpenBeans/releases).

## Compiling

To compile OpenBeans run

```sh
./build.sh clean
```

and see the resulting archives in the `CoolBeans-2018.12-$TIMESTAMP` folder.

### Dependencies

* `build-essential` Linux package or Command Line Tools for Xcode on macOS. The build system uses and bootstraps [pkgsrc](http://www.pkgsrc.org).
* `make` and `mingw-w64` to recompile the native Windows launchers.
* Ant 1.9.9 or above
* JDK 8 or 11

### Windows installers

The Windows installers are produced by running `win-build.bat` on a Windows machine. You'll need a digital certificate to sign the binaries.

The 32-bit Windows installers are produced by running `win-build32.bat`.

### macOS disk image

The macOS disk image is produced by running `sign-macos.sh` on a macOS machine. You'll need a Developer certificate to sign the app and disk image.

