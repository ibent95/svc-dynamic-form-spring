package svc.dynamic.form.project.Component;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class MimeTypesComponent {

  public static final String MIME_APPLICATION_ANDREW_INSET  = "application/andrew-inset";
  public static final String MIME_APPLICATION_JSON      = "application/json";
  public static final String MIME_APPLICATION_ZIP       = "application/zip";
  public static final String MIME_APPLICATION_X_GZIP      = "application/x-gzip";
  public static final String MIME_APPLICATION_TGZ       = "application/tgz";
  public static final String MIME_APPLICATION_MSWORD      = "application/msword";
  public static final String MIME_APPLICATION_POSTSCRIPT    = "application/postscript";
  public static final String MIME_APPLICATION_PDF       = "application/pdf";
  public static final String MIME_APPLICATION_JNLP      = "application/jnlp";
  public static final String MIME_APPLICATION_MAC_BINHEX40  = "application/mac-binhex40";
  public static final String MIME_APPLICATION_MAC_COMPACTPRO  = "application/mac-compactpro";
  public static final String MIME_APPLICATION_MATHML_XML    = "application/mathml+xml";
  public static final String MIME_APPLICATION_OCTET_STREAM  = "application/octet-stream";
  public static final String MIME_APPLICATION_ODA       = "application/oda";
  public static final String MIME_APPLICATION_RDF_XML     = "application/rdf+xml";
  public static final String MIME_APPLICATION_JAVA_ARCHIVE  = "application/java-archive";
  public static final String MIME_APPLICATION_RDF_SMIL    = "application/smil";
  public static final String MIME_APPLICATION_SRGS      = "application/srgs";
  public static final String MIME_APPLICATION_SRGS_XML    = "application/srgs+xml";
  public static final String MIME_APPLICATION_VND_MIF     = "application/vnd.mif";
  public static final String MIME_APPLICATION_VND_MSEXCEL   = "application/vnd.ms-excel";
  public static final String MIME_APPLICATION_VND_MSPOWERPOINT= "application/vnd.ms-powerpoint";
  public static final String MIME_APPLICATION_VND_RNREALMEDIA = "application/vnd.rn-realmedia";
  public static final String MIME_APPLICATION_X_BCPIO     = "application/x-bcpio";
  public static final String MIME_APPLICATION_X_CDLINK    = "application/x-cdlink";
  public static final String MIME_APPLICATION_X_CHESS_PGN   = "application/x-chess-pgn";
  public static final String MIME_APPLICATION_X_CPIO      = "application/x-cpio";
  public static final String MIME_APPLICATION_X_CSH     = "application/x-csh";
  public static final String MIME_APPLICATION_X_DIRECTOR    = "application/x-director";
  public static final String MIME_APPLICATION_X_DVI     = "application/x-dvi";
  public static final String MIME_APPLICATION_X_FUTURESPLASH  = "application/x-futuresplash";
  public static final String MIME_APPLICATION_X_GTAR      = "application/x-gtar";
  public static final String MIME_APPLICATION_X_HDF     = "application/x-hdf";
  public static final String MIME_APPLICATION_X_JAVASCRIPT  = "application/x-javascript";
  public static final String MIME_APPLICATION_X_KOAN      = "application/x-koan";
  public static final String MIME_APPLICATION_X_LATEX     = "application/x-latex";
  public static final String MIME_APPLICATION_X_NETCDF    = "application/x-netcdf";
  public static final String MIME_APPLICATION_X_OGG     = "application/x-ogg";
  public static final String MIME_APPLICATION_X_SH      = "application/x-sh";
  public static final String MIME_APPLICATION_X_SHAR      = "application/x-shar";
  public static final String MIME_APPLICATION_X_SHOCKWAVE_FLASH = "application/x-shockwave-flash";
  public static final String MIME_APPLICATION_X_STUFFIT     = "application/x-stuffit";
  public static final String MIME_APPLICATION_X_SV4CPIO     = "application/x-sv4cpio";
  public static final String MIME_APPLICATION_X_SV4CRC    = "application/x-sv4crc";
  public static final String MIME_APPLICATION_X_TAR       = "application/x-tar";
  public static final String MIME_APPLICATION_X_RAR_COMPRESSED= "application/x-rar-compressed";
  public static final String MIME_APPLICATION_X_TCL       = "application/x-tcl";
  public static final String MIME_APPLICATION_X_TEX       = "application/x-tex";
  public static final String MIME_APPLICATION_X_TEXINFO   = "application/x-texinfo";
  public static final String MIME_APPLICATION_X_TROFF     = "application/x-troff";
  public static final String MIME_APPLICATION_X_TROFF_MAN   = "application/x-troff-man";
  public static final String MIME_APPLICATION_X_TROFF_ME    = "application/x-troff-me";
  public static final String MIME_APPLICATION_X_TROFF_MS    = "application/x-troff-ms";
  public static final String MIME_APPLICATION_X_USTAR     = "application/x-ustar";
  public static final String MIME_APPLICATION_X_WAIS_SOURCE = "application/x-wais-source";
  public static final String MIME_APPLICATION_VND_MOZZILLA_XUL_XML = "application/vnd.mozilla.xul+xml";
  public static final String MIME_APPLICATION_XHTML_XML     = "application/xhtml+xml";
  public static final String MIME_APPLICATION_XSLT_XML    = "application/xslt+xml";
  public static final String MIME_APPLICATION_XML       = "application/xml";
  public static final String MIME_APPLICATION_XML_DTD     = "application/xml-dtd";
  public static final String MIME_IMAGE_BMP         = "image/bmp";
  public static final String MIME_IMAGE_CGM         = "image/cgm";
  public static final String MIME_IMAGE_GIF         = "image/gif";
  public static final String MIME_IMAGE_IEF         = "image/ief";
  public static final String MIME_IMAGE_JPEG          = "image/jpeg";
  public static final String MIME_IMAGE_TIFF          = "image/tiff";
  public static final String MIME_IMAGE_PNG         = "image/png";
  public static final String MIME_IMAGE_SVG_XML       = "image/svg+xml";
  public static final String MIME_IMAGE_VND_DJVU        = "image/vnd.djvu";
  public static final String MIME_IMAGE_WAP_WBMP        = "image/vnd.wap.wbmp";
  public static final String MIME_IMAGE_X_CMU_RASTER      = "image/x-cmu-raster";
  public static final String MIME_IMAGE_X_ICON        = "image/x-icon";
  public static final String MIME_IMAGE_X_PORTABLE_ANYMAP   = "image/x-portable-anymap";
  public static final String MIME_IMAGE_X_PORTABLE_BITMAP   = "image/x-portable-bitmap";
  public static final String MIME_IMAGE_X_PORTABLE_GRAYMAP  = "image/x-portable-graymap";
  public static final String MIME_IMAGE_X_PORTABLE_PIXMAP   = "image/x-portable-pixmap";
  public static final String MIME_IMAGE_X_RGB         = "image/x-rgb";
  public static final String MIME_AUDIO_BASIC         = "audio/basic";
  public static final String MIME_AUDIO_MIDI          = "audio/midi";
  public static final String MIME_AUDIO_MPEG          = "audio/mpeg";
  public static final String MIME_AUDIO_X_AIFF        = "audio/x-aiff";
  public static final String MIME_AUDIO_X_MPEGURL       = "audio/x-mpegurl";
  public static final String MIME_AUDIO_X_PN_REALAUDIO    = "audio/x-pn-realaudio";
  public static final String MIME_AUDIO_X_WAV         = "audio/x-wav";
  public static final String MIME_CHEMICAL_X_PDB        = "chemical/x-pdb";
  public static final String MIME_CHEMICAL_X_XYZ        = "chemical/x-xyz";
  public static final String MIME_MODEL_IGES          = "model/iges";
  public static final String MIME_MODEL_MESH          = "model/mesh";
  public static final String MIME_MODEL_VRLM          = "model/vrml";
  public static final String MIME_TEXT_PLAIN          = "text/plain";
  public static final String MIME_TEXT_RICHTEXT       = "text/richtext";
  public static final String MIME_TEXT_RTF          = "text/rtf";
  public static final String MIME_TEXT_HTML         = "text/html";
  public static final String MIME_TEXT_CALENDAR       = "text/calendar";
  public static final String MIME_TEXT_CSS          = "text/css";
  public static final String MIME_TEXT_SGML         = "text/sgml";
  public static final String MIME_TEXT_TAB_SEPARATED_VALUES = "text/tab-separated-values";
  public static final String MIME_TEXT_VND_WAP_XML      = "text/vnd.wap.wml";
  public static final String MIME_TEXT_VND_WAP_WMLSCRIPT    = "text/vnd.wap.wmlscript";
  public static final String MIME_TEXT_X_SETEXT       = "text/x-setext";
  public static final String MIME_TEXT_X_COMPONENT      = "text/x-component";
  public static final String MIME_VIDEO_QUICKTIME       = "video/quicktime";
  public static final String MIME_VIDEO_MPEG          = "video/mpeg";
  public static final String MIME_VIDEO_VND_MPEGURL     = "video/vnd.mpegurl";
  public static final String MIME_VIDEO_X_MSVIDEO       = "video/x-msvideo";
  public static final String MIME_VIDEO_X_MS_WMV        = "video/x-ms-wmv";
  public static final String MIME_VIDEO_X_SGI_MOVIE     = "video/x-sgi-movie";
  public static final String MIME_X_CONFERENCE_X_COOLTALK   = "x-conference/x-cooltalk";

  private static HashMap<String, String> mimeTypeMapping;

  public MimeTypesComponent() {
    this.mimeTypeMapping = new HashMap<String, String>(200) {

      private void put1(String key, String value) {
        if (put(key, value) != null) {
          throw new IllegalArgumentException("Duplicated extension: " + key);
        }
      }

      {
        this.put1("xul", MIME_APPLICATION_VND_MOZZILLA_XUL_XML);
        this.put1("json", MIME_APPLICATION_JSON);
        this.put1("ice", MIME_X_CONFERENCE_X_COOLTALK);
        this.put1("movie", MIME_VIDEO_X_SGI_MOVIE);
        this.put1("avi", MIME_VIDEO_X_MSVIDEO);
        this.put1("wmv", MIME_VIDEO_X_MS_WMV);
        this.put1("m4u", MIME_VIDEO_VND_MPEGURL);
        this.put1("mxu", MIME_VIDEO_VND_MPEGURL);
        this.put1("htc", MIME_TEXT_X_COMPONENT);
        this.put1("etx", MIME_TEXT_X_SETEXT);
        this.put1("wmls", MIME_TEXT_VND_WAP_WMLSCRIPT);
        this.put1("wml", MIME_TEXT_VND_WAP_XML);
        this.put1("tsv", MIME_TEXT_TAB_SEPARATED_VALUES);
        this.put1("sgm", MIME_TEXT_SGML);
        this.put1("sgml", MIME_TEXT_SGML);
        this.put1("css", MIME_TEXT_CSS);
        this.put1("ifb", MIME_TEXT_CALENDAR);
        this.put1("ics", MIME_TEXT_CALENDAR);
        this.put1("wrl", MIME_MODEL_VRLM);
        this.put1("vrlm", MIME_MODEL_VRLM);
        this.put1("silo", MIME_MODEL_MESH);
        this.put1("mesh", MIME_MODEL_MESH);
        this.put1("msh", MIME_MODEL_MESH);
        this.put1("iges", MIME_MODEL_IGES);
        this.put1("igs", MIME_MODEL_IGES);
        this.put1("rgb", MIME_IMAGE_X_RGB);
        this.put1("ppm", MIME_IMAGE_X_PORTABLE_PIXMAP);
        this.put1("pgm", MIME_IMAGE_X_PORTABLE_GRAYMAP);
        this.put1("pbm", MIME_IMAGE_X_PORTABLE_BITMAP);
        this.put1("pnm", MIME_IMAGE_X_PORTABLE_ANYMAP);
        this.put1("ico", MIME_IMAGE_X_ICON);
        this.put1("ras", MIME_IMAGE_X_CMU_RASTER);
        this.put1("wbmp", MIME_IMAGE_WAP_WBMP);
        this.put1("djv", MIME_IMAGE_VND_DJVU);
        this.put1("djvu", MIME_IMAGE_VND_DJVU);
        this.put1("svg", MIME_IMAGE_SVG_XML);
        this.put1("ief", MIME_IMAGE_IEF);
        this.put1("cgm", MIME_IMAGE_CGM);
        this.put1("bmp", MIME_IMAGE_BMP);
        this.put1("xyz", MIME_CHEMICAL_X_XYZ);
        this.put1("pdb", MIME_CHEMICAL_X_PDB);
        this.put1("ra", MIME_AUDIO_X_PN_REALAUDIO);
        this.put1("ram", MIME_AUDIO_X_PN_REALAUDIO);
        this.put1("m3u", MIME_AUDIO_X_MPEGURL);
        this.put1("aifc", MIME_AUDIO_X_AIFF);
        this.put1("aif", MIME_AUDIO_X_AIFF);
        this.put1("aiff", MIME_AUDIO_X_AIFF);
        this.put1("mp3", MIME_AUDIO_MPEG);
        this.put1("mp2", MIME_AUDIO_MPEG);
        this.put1("mp1", MIME_AUDIO_MPEG);
        this.put1("mpga", MIME_AUDIO_MPEG);
        this.put1("kar", MIME_AUDIO_MIDI);
        this.put1("mid", MIME_AUDIO_MIDI);
        this.put1("midi", MIME_AUDIO_MIDI);
        this.put1("dtd", MIME_APPLICATION_XML_DTD);
        this.put1("xsl", MIME_APPLICATION_XML);
        this.put1("xml", MIME_APPLICATION_XML);
        this.put1("xslt", MIME_APPLICATION_XSLT_XML);
        this.put1("xht", MIME_APPLICATION_XHTML_XML);
        this.put1("xhtml", MIME_APPLICATION_XHTML_XML);
        this.put1("src", MIME_APPLICATION_X_WAIS_SOURCE);
        this.put1("ustar", MIME_APPLICATION_X_USTAR);
        this.put1("ms", MIME_APPLICATION_X_TROFF_MS);
        this.put1("me", MIME_APPLICATION_X_TROFF_ME);
        this.put1("man", MIME_APPLICATION_X_TROFF_MAN);
        this.put1("roff", MIME_APPLICATION_X_TROFF);
        this.put1("tr", MIME_APPLICATION_X_TROFF);
        this.put1("t", MIME_APPLICATION_X_TROFF);
        this.put1("texi", MIME_APPLICATION_X_TEXINFO);
        this.put1("texinfo", MIME_APPLICATION_X_TEXINFO);
        this.put1("tex", MIME_APPLICATION_X_TEX);
        this.put1("tcl", MIME_APPLICATION_X_TCL);
        this.put1("sv4crc", MIME_APPLICATION_X_SV4CRC);
        this.put1("sv4cpio", MIME_APPLICATION_X_SV4CPIO);
        this.put1("sit", MIME_APPLICATION_X_STUFFIT);
        this.put1("swf", MIME_APPLICATION_X_SHOCKWAVE_FLASH);
        this.put1("shar", MIME_APPLICATION_X_SHAR);
        this.put1("sh", MIME_APPLICATION_X_SH);
        this.put1("cdf", MIME_APPLICATION_X_NETCDF);
        this.put1("nc", MIME_APPLICATION_X_NETCDF);
        this.put1("latex", MIME_APPLICATION_X_LATEX);
        this.put1("skm", MIME_APPLICATION_X_KOAN);
        this.put1("skt", MIME_APPLICATION_X_KOAN);
        this.put1("skd", MIME_APPLICATION_X_KOAN);
        this.put1("skp", MIME_APPLICATION_X_KOAN);
        this.put1("js", MIME_APPLICATION_X_JAVASCRIPT);
        this.put1("hdf", MIME_APPLICATION_X_HDF);
        this.put1("gtar", MIME_APPLICATION_X_GTAR);
        this.put1("spl", MIME_APPLICATION_X_FUTURESPLASH);
        this.put1("dvi", MIME_APPLICATION_X_DVI);
        this.put1("dxr", MIME_APPLICATION_X_DIRECTOR);
        this.put1("dir", MIME_APPLICATION_X_DIRECTOR);
        this.put1("dcr", MIME_APPLICATION_X_DIRECTOR);
        this.put1("csh", MIME_APPLICATION_X_CSH);
        this.put1("cpio", MIME_APPLICATION_X_CPIO);
        this.put1("pgn", MIME_APPLICATION_X_CHESS_PGN);
        this.put1("vcd", MIME_APPLICATION_X_CDLINK);
        this.put1("bcpio", MIME_APPLICATION_X_BCPIO);
        this.put1("rm", MIME_APPLICATION_VND_RNREALMEDIA);
        this.put1("ppt", MIME_APPLICATION_VND_MSPOWERPOINT);
        this.put1("mif", MIME_APPLICATION_VND_MIF);
        this.put1("grxml", MIME_APPLICATION_SRGS_XML);
        this.put1("gram", MIME_APPLICATION_SRGS);
        this.put1("smil", MIME_APPLICATION_RDF_SMIL);
        this.put1("smi", MIME_APPLICATION_RDF_SMIL);
        this.put1("rdf", MIME_APPLICATION_RDF_XML);
        this.put1("ogg", MIME_APPLICATION_X_OGG);
        this.put1("oda", MIME_APPLICATION_ODA);
        this.put1("dmg", MIME_APPLICATION_OCTET_STREAM);
        this.put1("lzh", MIME_APPLICATION_OCTET_STREAM);
        this.put1("so", MIME_APPLICATION_OCTET_STREAM);
        this.put1("lha", MIME_APPLICATION_OCTET_STREAM);
        this.put1("dms", MIME_APPLICATION_OCTET_STREAM);
        this.put1("bin", MIME_APPLICATION_OCTET_STREAM);
        this.put1("mathml", MIME_APPLICATION_MATHML_XML);
        this.put1("cpt", MIME_APPLICATION_MAC_COMPACTPRO);
        this.put1("hqx", MIME_APPLICATION_MAC_BINHEX40);
        this.put1("jnlp", MIME_APPLICATION_JNLP);
        this.put1("ez", MIME_APPLICATION_ANDREW_INSET);
        this.put1("txt", MIME_TEXT_PLAIN);
        this.put1("ini", MIME_TEXT_PLAIN);
        this.put1("c", MIME_TEXT_PLAIN);
        this.put1("h", MIME_TEXT_PLAIN);
        this.put1("cpp", MIME_TEXT_PLAIN);
        this.put1("cxx", MIME_TEXT_PLAIN);
        this.put1("cc", MIME_TEXT_PLAIN);
        this.put1("chh", MIME_TEXT_PLAIN);
        this.put1("java", MIME_TEXT_PLAIN);
        this.put1("csv", MIME_TEXT_PLAIN);
        this.put1("bat", MIME_TEXT_PLAIN);
        this.put1("cmd", MIME_TEXT_PLAIN);
        this.put1("asc", MIME_TEXT_PLAIN);
        this.put1("rtf", MIME_TEXT_RTF);
        this.put1("rtx", MIME_TEXT_RICHTEXT);
        this.put1("html", MIME_TEXT_HTML);
        this.put1("htm", MIME_TEXT_HTML);
        this.put1("zip", MIME_APPLICATION_ZIP);
        this.put1("rar", MIME_APPLICATION_X_RAR_COMPRESSED);
        this.put1("gzip", MIME_APPLICATION_X_GZIP);
        this.put1("gz", MIME_APPLICATION_X_GZIP);
        this.put1("tgz", MIME_APPLICATION_TGZ);
        this.put1("tar", MIME_APPLICATION_X_TAR);
        this.put1("gif", MIME_IMAGE_GIF);
        this.put1("jpeg", MIME_IMAGE_JPEG);
        this.put1("jpg", MIME_IMAGE_JPEG);
        this.put1("jpe", MIME_IMAGE_JPEG);
        this.put1("tiff", MIME_IMAGE_TIFF);
        this.put1("tif", MIME_IMAGE_TIFF);
        this.put1("png", MIME_IMAGE_PNG);
        this.put1("au", MIME_AUDIO_BASIC);
        this.put1("snd", MIME_AUDIO_BASIC);
        this.put1("wav", MIME_AUDIO_X_WAV);
        this.put1("mov", MIME_VIDEO_QUICKTIME);
        this.put1("qt", MIME_VIDEO_QUICKTIME);
        this.put1("mpeg", MIME_VIDEO_MPEG);
        this.put1("mpg", MIME_VIDEO_MPEG);
        this.put1("mpe", MIME_VIDEO_MPEG);
        this.put1("abs", MIME_VIDEO_MPEG);
        this.put1("doc", MIME_APPLICATION_MSWORD);
        this.put1("xls", MIME_APPLICATION_VND_MSEXCEL);
        this.put1("eps", MIME_APPLICATION_POSTSCRIPT);
        this.put1("ai", MIME_APPLICATION_POSTSCRIPT);
        this.put1("ps", MIME_APPLICATION_POSTSCRIPT);
        this.put1("pdf", MIME_APPLICATION_PDF);
        this.put1("exe", MIME_APPLICATION_OCTET_STREAM);
        this.put1("dll", MIME_APPLICATION_OCTET_STREAM);
        this.put1("class", MIME_APPLICATION_OCTET_STREAM);
        this.put1("jar", MIME_APPLICATION_JAVA_ARCHIVE);
      }
    };
  }

  /**
   * Registers MIME type for provided extension. Existing extension type will be overriden.
   */
  public void registerMimeType(String ext, String mimeType) {
    mimeTypeMapping.put(ext, mimeType);
  }

  /**
   * Returns the corresponding MIME type to the given extension.
   * If no MIME type was found it returns 'application/octet-stream' type.
   */
  public String getMimeType(String ext) {
    String mimeType = lookupMimeType(ext);
    if (mimeType == null) {
      mimeType = MIME_APPLICATION_OCTET_STREAM;
    }
    return mimeType;
  }

  /**
   * Simply returns MIME type or <code>null</code> if no type is found.
   */
  public String lookupMimeType(String ext) {
    return mimeTypeMapping.get(ext.toLowerCase());
  }

  /**
   * Simply returns extension if no type is found.
   */
  public String getExtension(String mimeType) {
    return mimeTypeMapping
              .entrySet().stream()
              .filter(
                mapping -> mapping.getValue().equals(mimeType)
              )
              .map(Map.Entry::getKey)
              .findFirst()
              .get()
              .toString();
  }

}