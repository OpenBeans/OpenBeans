#
#  Copyright (c) 2018, 2019 Emilian Marius Bold
#
#  Permission to use, copy, modify, and distribute this software for any
#  purpose with or without fee is hereby granted, provided that the above
#  copyright notice and this permission notice appear in all copies.
#
#  THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
#  WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
#  MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
#  ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
#  WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
#  ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
#  OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
#

PATCH_DIST_STRIP=-p1
PATCH_STRIP=-p1

DISTNAME    = windows 
PKGNAME     = windows-0.0
CATEGORIES  = devel
EXTRACT_SUFX=	.zip

DISTFILES= OpenJDK11U-jdk_x64_windows_hotspot_11.0.3_7.zip OpenJDK11U-jdk_x86-32_windows_hotspot_11.0.3_7.zip
SITES.OpenJDK11U-jdk_x64_windows_hotspot_11.0.3_7.zip = -https://github.com/AdoptOpenJDK/openjdk11-binaries/releases/download/jdk-11.0.3%2B7/OpenJDK11U-jdk_x64_windows_hotspot_11.0.3_7.zip
SITES.OpenJDK11U-jdk_x86-32_windows_hotspot_11.0.3_7.zip = -https://github.com/AdoptOpenJDK/openjdk11-binaries/releases/download/jdk-11.0.3%2B7/OpenJDK11U-jdk_x86-32_windows_hotspot_11.0.3_7.zip

WRKSRC=		${WRKDIR}

EXTRACT_DIR.OpenJDK11U-jdk_x86-32_windows_hotspot_11.0.3_7.zip = ${WRKSRC}/32bit

DEPENDS += coolbeans:../../coolbeans/ide

APP_VERSION=2018.12


pre-configure:
	${CP} -R ${FILESDIR}/* ${WRKSRC}

pre-install:
	${MKDIR} ${DESTDIR}${PREFIX}/windows/64bit
	${MKDIR} ${DESTDIR}${PREFIX}/windows/32bit
	#unzip coolbeans 
	../../mk/extract/extract -d ${DESTDIR}${PREFIX}/windows/64bit ${PREFIX}/zip/CoolBeans.tgz
	${MV} ${DESTDIR}${PREFIX}/windows/64bit/coolbeans-2018.12 ${DESTDIR}${PREFIX}/windows/64bit/coolbeans
	#and copy again on 32bit
	${CP} -R ${DESTDIR}${PREFIX}/windows/64bit/coolbeans ${DESTDIR}${PREFIX}/windows/32bit
	#copy installer stuff
	${CP} ${WRKSRC}/coolbeans-setup.iss ${DESTDIR}${PREFIX}/windows/64bit
	${CP} ${WRKSRC}/coolbeans-setup32.iss ${DESTDIR}${PREFIX}/windows/32bit
	${CP} ${WRKSRC}/coolbeans.ico ${DESTDIR}${PREFIX}/windows/32bit
	${CP} ${WRKSRC}/coolbeans.ico ${DESTDIR}${PREFIX}/windows/64bit
	${CP} ${WRKSRC}/win-build.bat ${DESTDIR}${PREFIX}/windows/64bit
	${CP} ${WRKSRC}/win-build32.bat ${DESTDIR}${PREFIX}/windows/32bit
	#copy 64bit JDK
	${CP} -R ${WRKSRC}/jdk-11.0.3+7 ${DESTDIR}${PREFIX}/windows/64bit/coolbeans/jre
	#copy 32bit JDK
	${CP} -R ${WRKSRC}/32bit/jdk-11.0.3+7 ${DESTDIR}${PREFIX}/windows/32bit/coolbeans/jre
	#make zips
	cd ${DESTDIR}${PREFIX}/windows; zip -9 ${DESTDIR}${PREFIX}/windows/CoolBeans-Windows.64bit.zip -r 64bit
	cd ${DESTDIR}${PREFIX}/windows; zip -9 ${DESTDIR}${PREFIX}/windows/CoolBeans-Windows.32bit.zip -r 32bit 
	#remove old dirs (which probably should have been created elsewhere...)
	${RM} -r ${DESTDIR}${PREFIX}/windows/64bit
	${RM} -r ${DESTDIR}${PREFIX}/windows/32bit

do-install:
	echo "Nothing"


.include "../../mk/bsd.pkg.mk"
