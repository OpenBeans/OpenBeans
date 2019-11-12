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

DISTNAME= openbeans
# a version name is required, can't just use openbsd
PKGNAME= openbeans-2019.06
CATEGORIES= devel
EXTRACT_SUFX=	.zip

DISTFILES=      release111.zip netbeans-releases.zip maven-search-remote.zip
SITES.netbeans-releases.zip = -https://github.com/emilianbold/netbeans-releases/archive/2fd6dc84c187e3c79a959b3ddb4da1a9703659c7.zip
SITES.release111.zip = -https://github.com/apache/netbeans/archive/d9ec4e569177f19413439e1775707928cc93e1a5.zip
SITES.maven-search-remote.zip = -https://bitbucket.org/emilianbold/maven.search.remote/get/1.1.zip

MAINTAINER=	INSERT_YOUR_MAIL_ADDRESS_HERE
HOMEPAGE=	https://github.com/apache/incubator-netbeans/
COMMENT=	TODO: Short description of the package
#LICENSE=	# TODO: (see mk/license.mk)

WRKSRC=		${WRKDIR}/incubator-netbeans-release100
NBRELEASESRC = ${WRKDIR}/netbeans-releases
#USE_LANGUAGES=	c c++

post-extract:
	# rename netbeans-releases folder
	${MV} ${WRKDIR}/netbeans-releases-2fd6dc84c187e3c79a959b3ddb4da1a9703659c7 ${NBRELEASESRC}
	# rename incubator-netbeans folder
	${MV} ${WRKDIR}/netbeans-d9ec4e569177f19413439e1775707928cc93e1a5 ${WRKSRC}
	#move maven-search
	${MV} ${WRKDIR}/emilianbold-maven.search.remote-da0c42b68c13/ro.emilianbold.modules.maven.search.remote ${WRKSRC}/java
	#remove standalone metadata
	${RM} ${WRKSRC}/java/ro.emilianbold.modules.maven.search.remote/nbproject/genfiles.properties
	${RM} ${WRKSRC}/java/ro.emilianbold.modules.maven.search.remote/nbproject/platform.properties
	#populate dlight
	${MKDIR} ${WRKSRC}/dlight
	${ECHO} "Copying to dligh"
	# generated with for i in `echo  dlight.kit,        dlight.libs.common,        dlight.remote,        dlight.remote.impl,        dlight.remote.ui,        dlight.sendto,        git.remote,        git.remote.cli,        mercurial.remote,        remotefs.versioning,        remotefs.versioning.api,        subversion.remote  | sed s/,//g`; do echo "\${CP} -R \${NBRELEASESRC}/$i \${WRKSRC}/dlight"; done;
	${CP} -R ${NBRELEASESRC}/dlight.kit ${WRKSRC}/dlight
	${CP} -R ${NBRELEASESRC}/dlight.libs.common ${WRKSRC}/dlight
	${CP} -R ${NBRELEASESRC}/dlight.remote ${WRKSRC}/dlight
	${CP} -R ${NBRELEASESRC}/dlight.remote.impl ${WRKSRC}/dlight
	${CP} -R ${NBRELEASESRC}/dlight.remote.ui ${WRKSRC}/dlight
	${CP} -R ${NBRELEASESRC}/dlight.sendto ${WRKSRC}/dlight
	${CP} -R ${NBRELEASESRC}/git.remote ${WRKSRC}/dlight
	${CP} -R ${NBRELEASESRC}/git.remote.cli ${WRKSRC}/dlight
	${CP} -R ${NBRELEASESRC}/mercurial.remote ${WRKSRC}/dlight
	${CP} -R ${NBRELEASESRC}/remotefs.versioning ${WRKSRC}/dlight
	${CP} -R ${NBRELEASESRC}/remotefs.versioning.api ${WRKSRC}/dlight
	${CP} -R ${NBRELEASESRC}/subversion.remote ${WRKSRC}/dlight
	${MKDIR} ${WRKSRC}/cnd
	${ECHO} "Copying cnd"
	${CP} -R ${NBRELEASESRC}/cnd ${WRKSRC}/cnd
	${CP} -R ${NBRELEASESRC}/cnd.analysis.api ${WRKSRC}/cnd
	${CP} -R ${NBRELEASESRC}/cnd.analysis.impl ${WRKSRC}/cnd
	${CP} -R ${NBRELEASESRC}/cnd.antlr ${WRKSRC}/cnd
	${CP} -R ${NBRELEASESRC}/cnd.api.model ${WRKSRC}/cnd
	${CP} -R ${NBRELEASESRC}/cnd.api.project ${WRKSRC}/cnd
	${CP} -R ${NBRELEASESRC}/cnd.api.remote ${WRKSRC}/cnd
	${CP} -R ${NBRELEASESRC}/cnd.api.remote.ui ${WRKSRC}/cnd
	${CP} -R ${NBRELEASESRC}/cnd.apt ${WRKSRC}/cnd
	${CP} -R ${NBRELEASESRC}/cnd.asm ${WRKSRC}/cnd
	${CP} -R ${NBRELEASESRC}/cnd.callgraph ${WRKSRC}/cnd
	${CP} -R ${NBRELEASESRC}/cnd.classview ${WRKSRC}/cnd
	${CP} -R ${NBRELEASESRC}/cnd.cncppunit ${WRKSRC}/cnd
	${CP} -R ${NBRELEASESRC}/cnd.completion ${WRKSRC}/cnd
	${CP} -R ${NBRELEASESRC}/cnd.debugger.common2 ${WRKSRC}/cnd
	${CP} -R ${NBRELEASESRC}/cnd.debugger.dbx ${WRKSRC}/cnd
	${CP} -R ${NBRELEASESRC}/cnd.debugger.gdb2 ${WRKSRC}/cnd
	${CP} -R ${NBRELEASESRC}/cnd.discovery ${WRKSRC}/cnd
	${CP} -R ${NBRELEASESRC}/cnd.dwarfdiscovery ${WRKSRC}/cnd
	${CP} -R ${NBRELEASESRC}/cnd.dwarfdump ${WRKSRC}/cnd
	${CP} -R ${NBRELEASESRC}/cnd.editor ${WRKSRC}/cnd
	${CP} -R ${NBRELEASESRC}/cnd.gotodeclaration ${WRKSRC}/cnd
	${CP} -R ${NBRELEASESRC}/cnd.highlight ${WRKSRC}/cnd
	${CP} -R ${NBRELEASESRC}/cnd.indexing ${WRKSRC}/cnd
	${CP} -R ${NBRELEASESRC}/cnd.kit ${WRKSRC}/cnd
	${CP} -R ${NBRELEASESRC}/cnd.lexer ${WRKSRC}/cnd
	${CP} -R ${NBRELEASESRC}/cnd.makeproject ${WRKSRC}/cnd
	${CP} -R ${NBRELEASESRC}/cnd.makeproject.source.bridge ${WRKSRC}/cnd
	${CP} -R ${NBRELEASESRC}/cnd.makeproject.ui ${WRKSRC}/cnd
	${CP} -R ${NBRELEASESRC}/cnd.model.jclank.bridge ${WRKSRC}/cnd
	${CP} -R ${NBRELEASESRC}/cnd.model.services ${WRKSRC}/cnd
	${CP} -R ${NBRELEASESRC}/cnd.modeldiscovery ${WRKSRC}/cnd
	${CP} -R ${NBRELEASESRC}/cnd.modelimpl ${WRKSRC}/cnd
	${CP} -R ${NBRELEASESRC}/cnd.modelui ${WRKSRC}/cnd
	${CP} -R ${NBRELEASESRC}/cnd.modelutil ${WRKSRC}/cnd
	${CP} -R ${NBRELEASESRC}/cnd.navigation ${WRKSRC}/cnd
	${CP} -R ${NBRELEASESRC}/cnd.qnavigator ${WRKSRC}/cnd
	${CP} -R ${NBRELEASESRC}/cnd.refactoring ${WRKSRC}/cnd
	${CP} -R ${NBRELEASESRC}/cnd.remote ${WRKSRC}/cnd
	${CP} -R ${NBRELEASESRC}/cnd.remote.projectui ${WRKSRC}/cnd
	${CP} -R ${NBRELEASESRC}/cnd.remote.ui ${WRKSRC}/cnd
	${CP} -R ${NBRELEASESRC}/cnd.repository ${WRKSRC}/cnd
	${CP} -R ${NBRELEASESRC}/cnd.repository.api ${WRKSRC}/cnd
	${CP} -R ${NBRELEASESRC}/cnd.script ${WRKSRC}/cnd
	${CP} -R ${NBRELEASESRC}/cnd.search ${WRKSRC}/cnd
	${CP} -R ${NBRELEASESRC}/cnd.simpleunit ${WRKSRC}/cnd
	${CP} -R ${NBRELEASESRC}/cnd.source ${WRKSRC}/cnd
	${CP} -R ${NBRELEASESRC}/cnd.spellchecker.bindings ${WRKSRC}/cnd
	${CP} -R ${NBRELEASESRC}/cnd.testrunner ${WRKSRC}/cnd
	${CP} -R ${NBRELEASESRC}/cnd.toolchain ${WRKSRC}/cnd
	${CP} -R ${NBRELEASESRC}/cnd.toolchain.ui ${WRKSRC}/cnd
	${CP} -R ${NBRELEASESRC}/cnd.ui ${WRKSRC}/cnd
	${CP} -R ${NBRELEASESRC}/cnd.utils ${WRKSRC}/cnd
	${CP} -R ${NBRELEASESRC}/cnd.utils.ui ${WRKSRC}/cnd
	${CP} -R ${NBRELEASESRC}/jellytools.cnd ${WRKSRC}/cnd
	${CP} -R ${NBRELEASESRC}/libs.clank ${WRKSRC}/cnd
	${CP} -R ${NBRELEASESRC}/libs.dbx.support ${WRKSRC}/cnd
	#cnd.diagnostics.clank
	${CP} -R ${NBRELEASESRC}/cnd.diagnostics.clank ${WRKSRC}/cnd
	#cndext
	${MKDIR} ${WRKSRC}/cndext
	${CP} -R ${NBRELEASESRC}/cnd.mixeddev ${WRKSRC}/cndext
	#libs.antlr3.devel
	${CP} -R ${NBRELEASESRC}/libs.antlr3.devel ${WRKSRC}

pre-configure:
	${CP} -R ${FILESDIR}/* ${WRKSRC}
	#dark icons
	${CP} ${WRKSRC}/nb/ide.branding/core.startup/src/org/netbeans/core/startup/about_nb.png ${WRKSRC}/nb/ide.branding/core.startup/src/org/netbeans/core/startup/about_dark_nb.png
	${CP} ${WRKSRC}/nb/ide.branding/core.startup/src/org/netbeans/core/startup/splash_nb.gif ${WRKSRC}/nb/ide.branding/core.startup/src/org/netbeans/core/startup/splash_dark_nb.gif
	${CP} ${WRKSRC}/platform/core.startup/src/org/netbeans/core/startup/splash.gif ${WRKSRC}/platform/core.startup/src/org/netbeans/core/startup/splash_dark.gif
	#git symlink...
	${CP} ${WRKSRC}/LICENSE ${WRKSRC}/nb/o.n.upgrader/src/org/netbeans/license/LICENSE.txt
	#copy icon to notification app
	${CP} ${WRKSRC}/nb/ide.branding/release/netbeans.icns ${WRKSRC}/platform/ro.emilianbold.notifications/release/terminal-notifier-1.7.1/terminal-notifier.app/Contents/Resources/Terminal.icns

post-build:
	${MKDIR} ${DESTDIR}${PREFIX}
	# this will create an openbeans folder in dest dir
	unzip -q ${WRKSRC}/nbbuild/NetBeans*.zip -d ${DESTDIR}${PREFIX}
	${MV} ${DESTDIR}${PREFIX}/openbeans ${DESTDIR}${PREFIX}/${PKGNAME}
	#${CP} -R ${WRKSRC}/nbbuild/netbeans ${DESTDIR}${PREFIX}/netbeans
	${CP} -R ${WRKSRC}/nbbuild/nbms ${DESTDIR}${PREFIX}/nbms

do-install:
	echo "Nothing"
	#${INSTALL_DATA_DIR} ${DESTDIR}/netbeans ${DESTDIR}${PREFIX}/share/coolbeans

.include "../../mk/bsd.pkg.mk"
