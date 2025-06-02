# YATGram — Unofficial Telegram Messenger for Android
![image](https://raw.githubusercontent.com/et-ness/YATGram/main/TMessagesProj/src/main/res/mipmap-xxxhdpi/ic_launcher.png) 
YATGram is a fork of the unofficial Telegram Fork Client

[![Build](https://img.shields.io/github/actions/workflow/status/et-ness/YATGram/build.yml?branch=main&event=push&logo=github&label=Build)](https://github.com/et-ness/YATGram/actions/workflows/build.yml?query=event%3Apush+branch%3Amain+is%3Acompleted) [![Download](https://img.shields.io/github/v/release/et-ness/YATGram?color=orange&logoColor=orange&label=Download&logo=DocuSign)](https://github.com/et-ness/YATGram/releases/latest) [![Total](https://shields.io/github/downloads/et-ness/YATGram/total?logo=Bookmeter&label=Counts&logoColor=yellow&color=yellow)](https://github.com/et-ness/YATGram/releases) [![TotalLatest](https://img.shields.io/github/downloads/et-ness/YATGram/latest/total?label=Counts%20for%20latest&logo=Bookmeter)](https://github.com/et-ness/YATGram/releases/latest)

## Credits:
- [Forkgram/Fork Client](https://github.com/forkgram/TelegramAndroid) principal fork of this proect
- [Nekox](https://github.com/NekoX-Dev/NekoX) unlimited accounts, qrlogin and others
- [Nagram](https://github.com/NextAlone/Nagram) Improvements of nekox's patch
- [MercuryGram](https://github.com/drizzt/Mercurygram) some patches
- [Telegram-FOSS](https://github.com/Telegram-FOSS-Team/Telegram-FOSS) Base of Forkgram
- [Official Telegram](https://github.com/DrKLO/Telegram) All updates of Telegram Android

## Features:
- All features of ForkGram/Fork Client
- Unlimited acccounts*
- Fix of delete for everyone
- QR Login
- Changed order of buttons in Chat
- Bypass restrict contents where is not allowed**
- Web Push notifications with UnifiedPush

<sub><sup>* Theoretically unlimited, but over 40 there are crashes in the app, if you want more than 40 accounts you can install version 2, same app with different package name, where you can configure other 40 accounts. I don't think it will ever be useful to anyone, but anyway you have it.</sup></sub>

<sub><sup>** Don't bypass contents protected with stars</sup></sub>
### Privacy Features:
- All features of ForkGram/Fork Client less show number in ProfileCell

## Requirement:
- Android 7 (API Level 24)

*Official Telegram require Android 6 but with update of NDK, Android 7 is required*

## Download and Update:

*At the moment the only source of updates is GitHub.*

| Source | Status | Version |
|--------|--------|---------|
| [GitHub](https://github.com/et-ness/YATGram/releases) / [Obtainium](https://github.com/ImranR98/Obtainium) | ![GitHub Release](https://img.shields.io/github/v/release/et-ness/YATGram?include_prereleases&logo=GitHub&label=GitHub) | arm64 / arm / both arm |

*From release 11.9.1.2 you have the splitted apk, one for each supported architecture arm64-v8a and armeabi-v7a or a single apk with all the arm versions. Choosing one of the versions saves about 20MB of space.*

## UnifiedPush:

UnifiedPush is FOSS. You can install your server and configure in one of the distributor apps or just use one free service as mozilla Web Push with SunUP

[Here](https://unifiedpush.org/users/distributors/) for more informations.

To make it work you need to install at least one distributor app and configure in Settings -> Fork Client Setting -> UnifiedPush provider, after restart the app.

Push notifications are encrypted with end-to-end encryption over https, only your client and telegram know the content.
