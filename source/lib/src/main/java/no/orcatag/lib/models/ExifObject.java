package no.orcatag.lib.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.OffsetDateTime;

/**
 * Created by bjorn on 10/10/2018.
 */
@Slf4j
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExifObject {


    /*
    GPS Map Datum: WGS84
Exif Version: 2.30
Compression Type: Baseline
Image Description:
Number of Components: 3
Component 2: Cb component: Quantization table 1, Sampling factors 1 horiz/1 vert
Focal Length: 30.3 mm
Component 1: Y component: Quantization table 0, Sampling factors 2 horiz/1 vert
tiff:ResolutionUnit: Inch
GPS Time-Stamp: 17:50:54.000 UTC
Date/Time Original: 2017:12:09 18:50:56
tiff:Make: NIKON
Component 3: Cr component: Quantization table 1, Sampling factors 1 horiz/1 vert
F-Number: f/4.5
Sensitivity Type: Standard Output Sensitivity
tiff:BitsPerSample: 8
Caption Digest: 181 220 167 92 99 110 186 233 62 212 120 108 237 98 80 156
GPS Latitude Ref: N
meta:creation-date: 2017-12-09T19:50:56
File Source: Digital Still Camera (DSC)
GPS Altitude Ref: Below sea level
Creation-Date: 2017-12-09T19:50:56
Make: NIKON
Orientation: Top, left side (Horizontal / normal)
Metering Mode: Multi-segment
tiff:Orientation: 1
Contrast: None
tiff:Software: COOLPIX P900   V1.3
Gain Control: High gain down
geo:long: -61.235161
Exif Image Height: 886 pixels
tiff:YResolution: 300.0
Y Resolution: 300 dots per inch
dc:description:
GPS Latitude: 10° 16' 6.25"
Coded Character Set: UTF-8
White Balance: Unknown
Thumbnail Height Pixels: 0
Last-Modified: 2017-12-09T19:50:56
exif:ExposureTime: 0.016666666666666666
File Size: 523122 bytes
Resolution Units: none
File Modified Date: Thu Oct 11 14:12:44 +02:00 2018
Date/Time: 2017:12:09 18:50:56
Caption/Abstract:
Exif Image Width: 1181 pixels
Image Height: 886 pixels
Thumbnail Width Pixels: 0
tiff:Model: COOLPIX P900
exif:IsoSpeedRatings: 110
GPS Date Stamp: 2017:12:09
Model: COOLPIX P900
tiff:ImageWidth: 1181
White Balance Mode: Auto white balance
date: 2017-12-09T19:50:56
Number of Tables: 4 Huffman tables
Components Configuration: YCbCr
X Resolution: 300 dots per inch
modified: 2017-12-09T19:50:56
Focal Length 35: 170 mm
Exposure Program: Program normal
Application Record Version: 2
Time Created: 18:50:56
Digital Zoom Ratio: Digital zoom not used
GPS Satellites: 13
GPS Version ID: 2.300
exif:FNumber: 4.5
Exposure Time: 1/60 sec
Digital Date Created: 2017:12:09
ISO Speed Ratings: 110
GPS Longitude: -61° 14' 6.58"
GPS Longitude Ref: W
exif:FocalLength: 30.3
XMP Value Count: 7
Software: COOLPIX P900   V1.3
Date Created: 2017:12:09
Scene Type: Directly photographed image
FlashPix Version: 1.00
geo:lat: 10.268403
Data Precision: 8 bits
Compressed Bits Per Pixel: 4 bits/pixel
tiff:ImageLength: 886
description:
dcterms:created: 2017-12-09T19:50:56
dcterms:modified: 2017-12-09T19:50:56
exif:Flash: false
Last-Save-Date: 2017-12-09T19:50:56
Digital Time Created: 18:50:56
GPS Altitude: 4 metres
Color Space: sRGB
meta:save-date: 2017-12-09T19:50:56
Date/Time Digitized: 2017:12:09 18:50:56
File Name: apache-tika-5866381198921067821.tmp
Flash: Flash did not fire, auto
Content-Type: image/jpeg
X-Parsed-By: org.apache.tika.parser.DefaultParser
tiff:XResolution: 300.0
Saturation: None
exif:DateTimeOriginal: 2017-12-09T19:50:56
Sharpness: None
Image Width: 1181 pixels
Resolution Unit: Inch
Exposure Bias Value: 0 EV
Subject Distance Range: Unknown
Max Aperture Value: f/2.7
Exposure Mode: Auto exposure
Scene Capture Type: Standard
Custom Rendered: Normal process
     */
    private Exif exif;
    private Geo geo;
    private Meta meta;

    public ExifObject(FileInputStream inputStream) {
        Parser parser = new AutoDetectParser();
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();

        ParseContext context = new ParseContext();
        try {
            parser.parse(inputStream, handler, metadata, context);
            if (log.isDebugEnabled()) {
                String[] metadataNames = metadata.names();
                for (String name : metadataNames) {
                    log.debug(name + ": " + metadata.get(name));
                }
            }
            exif = new Exif(metadata);
            geo = new Geo(metadata);
            inputStream.close();
        } catch (TikaException ex) {
            log.error("Unable to parse Exif");
            log.error(ex.getMessage(), ex);
        } catch (SAXException ex) {
            log.error("Unable to parse Exif");
            log.error(ex.getMessage(), ex);
        } catch (IOException ex) {
            log.error("Unable to parse Exif");
            log.error(ex.getMessage(), ex);
        }
    }


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Exif {
        private Double version;
        public Exif(Metadata metadata) {
            if (metadata.get("Exif Version") != null) {
                this.version = Double.valueOf(metadata.get("Exif Version"));
            }
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Geo {
        private Double lat;
        private Double lng;
        public Geo(Metadata metadata) {
            if (metadata.get("geo:lat") != null) {
                this.lat = Double.valueOf(metadata.get("geo:lat"));
            }
            if (metadata.get("geo:long") != null) {
                this.lng = Double.valueOf(metadata.get("geo:long"));
            }
        }

    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Meta {
        private OffsetDateTime creationDate;
        private OffsetDateTime saveDate;
        private String author;

        public Meta(Metadata metadata) {
            if (metadata.get("meta:creation-date") != null) {
                this.creationDate = OffsetDateTime.parse(metadata.get("meta:creation-date"));
            }
            if (metadata.get("meta:save-date") != null) {
                this.saveDate = OffsetDateTime.parse(metadata.get("meta:save-date"));
            }
            if (metadata.get("meta:author") != null) {
                this.author = metadata.get("meta:author");
            }
        }
    }
}
