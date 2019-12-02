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

DISTNAME    = macos 
PKGNAME     = macos-0.0
CATEGORIES  = devel
EXTRACT_SUFX=	.tar.gz

DISTFILES= OpenJDK11U-jdk_x64_mac_hotspot_11.0.5_10.tar.gz
SITES.OpenJDK11U-jdk_x64_mac_hotspot_11.0.5_10.tar.gz = -https://github.com/AdoptOpenJDK/openjdk11-binaries/releases/download/jdk-11.0.5%2B10/OpenJDK11U-jdk_x64_mac_hotspot_11.0.5_10.tar.gz

WRKSRC=		${WRKDIR}
#/jsparser

DEPENDS += openbeans:../../coolbeans/ide

APP_VERSION=2019.12

pre-configure:
	${CP} -R ${FILESDIR}/* ${WRKSRC}

do-build:
	${MKDIR} ${WRKSRC}/OpenBeans.app/Contents/MacOS
	${MKDIR} ${WRKSRC}/OpenBeans.app/Contents/Resources
	${SED} "s/NETBEANS_VERSION/${APP_VERSION}/g" ${WRKSRC}/Info.plist > ${WRKSRC}/OpenBeans.app/Contents/Info.plist
	${CP} -R ${PREFIX}/openbeans-2019.12 ${WRKSRC}/OpenBeans.app/Contents/Resources/openbeans
	${CP} ${WRKSRC}/original-netbeans ${WRKSRC}/OpenBeans.app/Contents/MacOS/netbeans
	${CHMOD} +x ${WRKSRC}/OpenBeans.app/Contents/MacOS/netbeans
	${CP} ${WRKSRC}/OpenBeans.app/Contents/Resources/openbeans/nb/netbeans.icns ${WRKSRC}/OpenBeans.app/Contents/Resources/
	#copy JRE
	#TODO: delete jre/lib/src.zip to save space?
	${CP} -R ${WRKSRC}/jdk-11.0.5+10/Contents/Home/ ${WRKSRC}/OpenBeans.app/Contents/Resources/openbeans/jre

do-install:
	${MKDIR} ${DESTDIR}${PREFIX}/macos
	${MKDIR} ${DESTDIR}${PREFIX}/zip
	${TAR} cvfz ${DESTDIR}${PREFIX}/macos/OpenBeans.app.tgz -C ${WRKSRC} OpenBeans.app
	${TAR} cvfz ${DESTDIR}${PREFIX}/zip/OpenBeans.tgz -C ${PREFIX}/ openbeans-2019.12
	${CP} ${WRKSRC}/sign-macos.sh ${DESTDIR}${PREFIX}/macos

.include "../../mk/bsd.pkg.mk"
