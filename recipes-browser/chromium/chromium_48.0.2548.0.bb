include chromium-browser.inc

SRC_URI += "\
        file://chromium-48/add_missing_stat_h_include.patch \
        file://chromium-48/0003-Remove-hard-coded-values-for-CC-and-CXX.patch \
        file://chromium-48/0004-Create-empty-i18n_process_css_test.html-file-to-avoi.patch \
        file://chromium-48/0005-Override-root-filesystem-access-restriction.patch \
        file://chromium-48/0011-Replace-readdir_r-with-readdir.patch \
        file://chromium-48/remove-Werror.patch \
        ${@bb.utils.contains('PACKAGECONFIG', 'ignore-lost-context', 'file://chromium-48/0001-Remove-accelerated-Canvas-support-from-blacklist.patch', '', d)} \
        ${@bb.utils.contains('PACKAGECONFIG', 'disable-api-keys-info-bar', 'file://chromium-48/0002-Disable-API-keys-info-bar.patch', '', d)} \
"

SRC_URI_append_aarch64 += "\
        file://fix_64_bit_builds.patch \
"

LIC_FILES_CHKSUM = "file://LICENSE;md5=0fca02217a5d49a14dfe2d11837bb34d"
SRC_URI[md5sum] = "0534981cc21efcd11e64b67b85854420"
SRC_URI[sha256sum] = "4ca4e2adb340b3fb4d502266ad7d6bda45fa3519906dbf63cce11a63f680dbc8"

EXTRA_OEGYP += " \
	-Dv8_use_external_startup_data=0 \
"

OZONE_WAYLAND_GIT_BRANCH = "Milestone-SouthSister"
OZONE_WAYLAND_GIT_SRCREV = "c605505044af3345a276abbd7c29fd53db1dea40"

OZONE_WAYLAND_EXTRA_PATCHES = " \
	file://chromium-48/0006-Remove-GBM-support-from-wayland.gyp.patch \
"

# Component build is unsupported in ozone-wayland for Chromium 48
python() {
    if (d.getVar('CHROMIUM_ENABLE_WAYLAND', True) == '1'):
        if bb.utils.contains('PACKAGECONFIG', 'component-build', True, False, d):
            bb.fatal("Chromium 48 Wayland version cannot be built in component-mode")
}

CHROMIUM_X11_DEPENDS = "xextproto gtk+ libxi libxss"
CHROMIUM_X11_GYP_DEFINES = ""
CHROMIUM_WAYLAND_DEPENDS = "wayland libxkbcommon"
CHROMIUM_WAYLAND_GYP_DEFINES = "use_ash=1 use_aura=1 chromeos=0 use_ozone=1 use_xkbcommon=1"
