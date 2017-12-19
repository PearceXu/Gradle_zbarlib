#
# Android NDK makefile 
#
# build - <ndk path>/ndk-build ICONV_SRC=<iconv library src> 
# clean -  <ndk path>/ndk-build clean
#
MY_LOCAL_PATH := $(call my-dir)

# libiconv
include $(CLEAR_VARS)
ICONV_SRC=$(MY_LOCAL_PATH)/../libiconv
LOCAL_PATH := $(ICONV_SRC)

LOCAL_MODULE := libiconv

LOCAL_CFLAGS := \
    -Wno-multichar \
    -D_ANDROID \
    -DLIBDIR=\"c\" \
    -DBUILDING_LIBICONV \
    -DBUILDING_LIBCHARSET \
    -DIN_LIBRAR

LOCAL_SRC_FILES := \
	lib/iconv.c \
	libcharset/lib/localcharset.c \
	lib/relocatable.c

LOCAL_C_INCLUDES := \
	$(ICONV_SRC)/include \
	$(ICONV_SRC)/libcharset \
	$(ICONV_SRC)/libcharset/include


include $(BUILD_SHARED_LIBRARY)

LOCAL_LDLIBS := -llog -lcharset

# libzbarjni
include $(CLEAR_VARS)

LOCAL_PATH := $(MY_LOCAL_PATH)
LOCAL_MODULE := zbarjni


LOCAL_SRC_FILES := ../ZBar/java/zbarjni.c \
		   ../ZBar/zbar/img_scanner.c \
		   ../ZBar/zbar/decoder.c \
		   ../ZBar/zbar/image.c \
		   ../ZBar/zbar/symbol.c \
		   ../ZBar/zbar/convert.c \
		   ../ZBar/zbar/config.c \
		   ../ZBar/zbar/scanner.c \
		   ../ZBar/zbar/error.c \
		   ../ZBar/zbar/refcnt.c \
		   ../ZBar/zbar/video.c \
		   ../ZBar/zbar/video/null.c \
		   ../ZBar/zbar/decoder/code128.c \
		   ../ZBar/zbar/decoder/code39.c \
		   ../ZBar/zbar/decoder/code93.c \
		   ../ZBar/zbar/decoder/codabar.c \
		   ../ZBar/zbar/decoder/databar.c \
		   ../ZBar/zbar/decoder/ean.c \
		   ../ZBar/zbar/decoder/i25.c \
		   ../ZBar/zbar/decoder/qr_finder.c \
		   ../ZBar/zbar/qrcode/bch15_5.c \
		   ../ZBar/zbar/qrcode/binarize.c \
		   ../ZBar/zbar/qrcode/isaac.c \
		   ../ZBar/zbar/qrcode/qrdec.c \
		   ../ZBar/zbar/qrcode/qrdectxt.c \
		   ../ZBar/zbar/qrcode/rs.c \
		   ../ZBar/zbar/qrcode/util.c


LOCAL_C_INCLUDES := \
            $(MY_LOCAL_PATH)/../ZBar/android/jni \
            $(MY_LOCAL_PATH)/../ZBar/include \
		    $(MY_LOCAL_PATH)/../ZBar/zbar \
		    $(ICONV_SRC)/include



LOCAL_SHARED_LIBRARIES := libiconv

include $(BUILD_SHARED_LIBRARY)