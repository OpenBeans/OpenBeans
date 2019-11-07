#!/bin/sh
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

tar xvfz CoolBeans.app.tgz

#make sure it's executable
chmod +x CoolBeans.app/Contents/Resources/coolbeans/platform/terminal-notifier-1.7.1/terminal-notifier.app/Contents/MacOS/terminal-notifier

codesign -s 7B8M7A5USJ "CoolBeans.app"

DMG="CoolBeans-2019.06.dmg"

hdiutil create -volname "CoolBeans 2019.06" -srcfolder "CoolBeans.app" -ov -format UDZO "$DMG"

xattr -rc "$DMG"

codesign -s 7B8M7A5USJ -v "$DMG"
