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

DISTNAME    = nbjavac
PKGNAME     = nbjavac-0.0
CATEGORIES  = devel
EXTRACT_SUFX=	.zip

DISTFILES=      nb-java-x2.zip
SITES.nb-java-x2.zip = -https://github.com/emilianbold/netbeans-nb-java-x/archive/0ac625b828a0ce47e1818db90b4096d66aa986cb.zip

MAINTAINER=	INSERT_YOUR_MAIL_ADDRESS_HERE

WRKSRC=		${WRKDIR}/nb-java-x

DEPENDS += coolbeans:../../coolbeans/ide

MAKE_ENV+= PATH_TO_NETBEANS=${PKGDIR}/../ide/work/incubator-netbeans-release100/nbbuild/netbeans

post-extract:
	${MV} ${WRKDIR}/netbeans-nb-java-x-0ac625b828a0ce47e1818db90b4096d66aa986cb ${WRKSRC}

pre-configure:
	${CP} -R ${FILESDIR}/* ${WRKSRC}

post-build:
	${MKDIR} ${DESTDIR}${PREFIX}/nbms
	${MKDIR} ${DESTDIR}${PREFIX}/coolbeans-2018.12/etc
	# copy nbms
	${CP} -R ${WRKSRC}/make/langtools/netbeans/nb-javac-modules/build/updates ${DESTDIR}${PREFIX}/nbms/nb-javac
	# copy cluster
	${CP} -R ${WRKSRC}/make/langtools/netbeans/nb-javac-modules/build/cluster ${DESTDIR}${PREFIX}/coolbeans-2018.12/nbjavac

do-install:
	# register cluster, as a separate file
	#echo 'nbjavac' > ${DESTDIR}${PREFIX}/coolbeans-2018.12/etc/netbeans_nbjavac.clusters
	echo 'Nothing'

.include "../../mk/bsd.pkg.mk"
