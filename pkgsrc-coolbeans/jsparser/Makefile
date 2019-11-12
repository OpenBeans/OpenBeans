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

DISTNAME    = jsparser 
PKGNAME     = jsparser-0.0
CATEGORIES  = devel
EXTRACT_SUFX=	.zip

DISTFILES=     jsparser.zip
SITES.jsparser.zip = -http://plugins.netbeans.org/download/plugin/9807

MAINTAINER=	INSERT_YOUR_MAIL_ADDRESS_HERE

EXTRACT_OPTS_ZIP=-d jsparser
WRKSRC=		${WRKDIR}/jsparser

DEPENDS += openbeans:../../coolbeans/ide

pre-configure:
	${CP} -R ${FILESDIR}/* ${WRKSRC}

do-install:
	${MKDIR} ${DESTDIR}${PREFIX}/nbms/jsparser
	${MKDIR} ${DESTDIR}${PREFIX}/openbeans-2019.06/webcommon/modules/ext
	# register cluster, as a separate file, if needed. But using webcommon by default
	#${MKDIR} ${DESTDIR}${PREFIX}/openbeans-2019.06/etc
	#echo 'jsparser' > ${DESTDIR}${PREFIX}/openbeans-2019.06/etc/netbeans_jsparser.clusters
	# create cluster
	unpack200 ${WRKSRC}/netbeans/modules/ext/com.oracle.js.parser.jar.pack.gz ${DESTDIR}${PREFIX}/openbeans-2019.06/webcommon/modules/ext/com.oracle.js.parser.jar
	unpack200 ${WRKSRC}/netbeans/modules/org-netbeans-libs-oracle-jsparser.jar.pack.gz ${DESTDIR}${PREFIX}/openbeans-2019.06/webcommon/modules/org-netbeans-libs-oracle-jsparser.jar
	${CP} -R ${WRKSRC}/netbeans/config ${DESTDIR}${PREFIX}/openbeans-2019.06/webcommon/config
	${CP} -R ${WRKSRC}/netbeans/update_tracking ${DESTDIR}${PREFIX}/openbeans-2019.06/webcommon
	# nbms	
	${CP} ${WRKSRC}/catalog.xml ${DESTDIR}${PREFIX}/nbms/jsparser/updates.xml
	${CP} ${DISTDIR}/jsparser.zip ${DESTDIR}${PREFIX}/nbms/jsparser/jsparser.nbm

.include "../../mk/bsd.pkg.mk"
