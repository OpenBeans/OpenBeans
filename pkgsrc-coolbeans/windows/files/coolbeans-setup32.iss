[ISPP]
; #expr Exec("ReplaceVistaIcon.exe", "coolbeans\bin\netbeans.exe coolbeans.ico", ".")

#define AppURL "http://www.openbeans.org"
#define appname "OpenBeans"
#define appnamelowercase "openbeans"
#define appcopyrightprefix  "Copyright (C) 2018-2019"
#define apppublisher "Joseki Bold SRL"
#define appemail "emi@openbeans.org"
#define appsourcedir "openbeans"
#define licensefile appsourcedir + "\LICENSE"
#define iconfile "coolbeans.ico"
#define SrcApp "netbeans" + ".exe"

#define appversion "2019.06"

[Setup]
; (To generate a new GUID, click Tools | Generate GUID inside the IDE.)
AppId={{F07FC588-48FA-4A2F-89BD-526D8B23DF44}
AppName={#appname}
AppVersion={#appversion}
AppVerName={#appname} {#appversion}
AppCopyright={#appcopyrightprefix} {#apppublisher}
AppPublisher={#apppublisher}
AppPublisherURL={#AppURL}
AppSupportURL={#AppURL}
AppUpdatesURL={#AppURL}
AppReadmeFile={#AppURL}
AppContact={#appemail}

UninstallDisplayName={#appname} {#appversion}
UninstallDisplayIcon={#iconfile}

VersionInfoVersion={#appversion}
DefaultDirName={pf}\{#appname}
DisableProgramGroupPage=yes

LicenseFile= {#licensefile}
OutputBaseFilename={#appname} Setup
SetupIconFile={#iconfile}
Compression=lzma
SolidCompression=yes

;ArchitecturesAllowed=x64
;ArchitecturesInstallIn64BitMode=x64

; PrivilegesRequired=admin
AllowNetworkDrive=no
AllowUNCPath=no

; ChangesAssociations=yes
; ChangesEnvironment=yes
CloseApplications=yes
SignTool=signtool.exe sign /a /fd SHA256 /tr http://rfc3161timestamp.globalsign.com/advanced /td SHA256 $f
SignedUninstaller=yes


[Tasks]
Name: "desktopicon"; Description: "{cm:CreateDesktopIcon}"; GroupDescription: "{cm:AdditionalIcons}"; Flags: unchecked


[Files]
Source: "{#appsourcedir}\bin\{#SrcApp}"; DestDir: "{app}\bin"; Flags: ignoreversion sign
Source: "{#appsourcedir}\*"; DestDir: "{app}"; Flags: ignoreversion recursesubdirs createallsubdirs

[Icons]
Name: "{commonprograms}\{#appname}"; Filename: "{app}\bin\{#SrcApp}"; Parameters: "--jdkhome ""{app}\jre"""
Name: "{commondesktop}\{#appname}"; Filename: "{app}\bin\{#SrcApp}"; Parameters: "--jdkhome ""{app}\jre"""; Tasks: desktopicon

[Run]
Filename: "{app}\bin\{#SrcApp}"; Description: "{cm:LaunchProgram,{#StringChange(appname, '&', '&&')}}"; Flags: nowait postinstall skipifsilent

