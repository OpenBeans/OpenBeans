#!/bin/bash
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
set -e

TIMESTAMP=`date +%Y%m%d-%H%M%S`

if [ "clean" = "$1" ]; then
    [ -x BUILD/pkgsrc ]             && (chmod -R u+w BUILD/pkgsrc && rm -r BUILD/pkgsrc)

    pushd BUILD
    #prepare pkgsrc tree
    if [ ! -f "pkgsrc-83e635cd1f63989b74bf59fc873c3fba70287a7d.zip" ]; then
        curl -L -o pkgsrc-83e635cd1f63989b74bf59fc873c3fba70287a7d.zip https://github.com/NetBSD/pkgsrc/archive/83e635cd1f63989b74bf59fc873c3fba70287a7d.zip
    fi;

    # extract everything
    unzip pkgsrc-83e635cd1f63989b74bf59fc873c3fba70287a7d.zip

    #rename folder
    mv pkgsrc-83e635cd1f63989b74bf59fc873c3fba70287a7d pkgsrc

    if [ -d 'distfiles' ]; then
        if [ -d 'pkgsrc/distfiles' ]; then
            rm -r pkgsrc/distfiles
        fi
        ln -s `pwd`/distfiles pkgsrc/distfiles
    fi;

    #bootstrap, otherwise keep the pkg as compiling takes some time
    if [ ! -d 'pkg' ]; then
        #bootstrap pkgsrc
        (cd pkgsrc/bootstrap; ./bootstrap --prefix `pwd`/../../pkg --unprivileged)
    fi;

    #BUILD
    popd

    #copy coolbeans pkgsrc subtree
    cp -R pkgsrc-coolbeans BUILD/pkgsrc/coolbeans
    #(cd BUILD/pkgsrc/coolbeans/ide; ~/pkg/bin/bmake configure )
    
    #verify pkgsrc patch
    #
    #[ -x BUILD/incubator-netbeans ] && (chmod -R u+w BUILD/incubator-netbeans && rm -r BUILD/incubator-netbeans)
    #git clone ~/work/incubator-netbeans BUILD/incubator-netbeans
    #
    #diff -ru --exclude javadoc-generic.css --exclude .DS_Store --exclude NB1216449736.0 --exclude="*.orig" BUILD/pkgsrc/coolbeans/ide/work/incubator-netbeans-release100/ BUILD/incubator-netbeans
fi;

PKGHOME=`pwd`/BUILD/pkg

(cd BUILD/pkgsrc/coolbeans/ide; ${PKGHOME}/bin/bmake install)
(cd BUILD/pkgsrc/coolbeans/macos; ${PKGHOME}/bin/bmake install)
(cd BUILD/pkgsrc/coolbeans/windows; ${PKGHOME}/bin/bmake install)

WORK="CoolBeans-2018.12-$TIMESTAMP"
mkdir $WORK || echo 'Dir exists' 

#TODO: Copy pkgsrc packages (and thus including NBMs, etc) to $WORK
cp -R ${PKGHOME}/nbms/ $WORK/nbms

cp -R ${PKGHOME}/macos ${WORK}/macos
cp -R ${PKGHOME}/zip ${WORK}/zip
cp -R ${PKGHOME}/windows ${WORK}/windows
