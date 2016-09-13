/*
 * Copyright 2002 Felix Pahl. All rights reserved.
 * Use is subject to license terms.
 */

package info.joriki.cff;

public interface CFFSpeaker
{
  final static String [] standardStrings = {
    ".notdef","space","exclam","quotedbl","numbersign",
    "dollar","percent","ampersand","quoteright","parenleft",
    "parenright","asterisk","plus","comma","hyphen",
    "period","slash","zero","one","two",
    "three","four","five","six","seven",
    "eight","nine","colon","semicolon","less",
    "equal","greater","question","at","A",
    "B","C","D","E","F",
    "G","H","I","J","K",
    "L","M","N","O","P",
    // 50
    "Q","R","S","T","U",
    "V","W","X","Y","Z",
    "bracketleft","backslash","bracketright","asciicircum","underscore",
    "quoteleft","a","b","c","d",
    "e","f","g","h","i",
    "j","k","l","m","n",
    "o","p","q","r","s",
    "t","u","v","w","x",
    "y","z","braceleft","bar","braceright",
    "asciitilde","exclamdown","cent","sterling","fraction",
    // 100
    "yen","florin","section","currency","quotesingle",
    "quotedblleft","guillemotleft","guilsinglleft","guilsinglright","fi",
    "fl","endash","dagger","daggerdbl","periodcentered",
    "paragraph","bullet","quotesinglbase","quotedblbase","quotedblright",
    "guillemotright","ellipsis","perthousand","questiondown","grave",
    "acute","circumflex","tilde","macron","breve",
    "dotaccent","dieresis","ring","cedilla","hungarumlaut",
    "ogonek","caron","emdash","AE","ordfeminine",
    "Lslash","Oslash","OE","ordmasculine","ae",
    "dotlessi","lslash","oslash","oe","germandbls",
    // 150
    "onesuperior","logicalnot","mu","trademark","Eth",
    "onehalf","plusminus","Thorn","onequarter","divide",
    "brokenbar","degree","thorn","threequarters","twosuperior",
    "registered","minus","eth","multiply","threesuperior",
    "copyright","Aacute","Acircumflex","Adieresis","Agrave",
    "Aring","Atilde","Ccedilla","Eacute","Ecircumflex",
    "Edieresis","Egrave","Iacute","Icircumflex","Idieresis",
    "Igrave","Ntilde","Oacute","Ocircumflex","Odieresis",
    "Ograve","Otilde","Scaron","Uacute","Ucircumflex",
    "Udieresis","Ugrave","Yacute","Ydieresis","Zcaron",
    // 200
    "aacute","acircumflex","adieresis","agrave","aring",
    "atilde","ccedilla","eacute","ecircumflex","edieresis",
    "egrave","iacute","icircumflex","idieresis","igrave",
    "ntilde","oacute","ocircumflex","odieresis","ograve",
    "otilde","scaron","uacute","ucircumflex","udieresis",
    "ugrave","yacute","ydieresis","zcaron","exclamsmall",
    "Hungarumlautsmall","dollaroldstyle","dollarsuperior","ampersandsmall","Acutesmall",
    "parenleftsuperior","parenrightsuperior","twodotenleader","onedotenleader","zerooldstyle",
    "oneoldstyle","twooldstyle","threeoldstyle","fouroldstyle","fiveoldstyle",
    "sixoldstyle","sevenoldstyle","eightoldstyle","nineoldstyle","commasuperior",
    // 250
    "threequartersemdash","periodsuperior","questionsmall","asuperior","bsuperior",
    "centsuperior","dsuperior","esuperior","isuperior","lsuperior",
    "msuperior","nsuperior","osuperior","rsuperior","ssuperior",
    "tsuperior","ff","ffi","ffl","parenleftinferior",
    "parenrightinferior","Circumflexsmall","hyphensuperior","Gravesmall","Asmall",
    "Bsmall","Csmall","Dsmall","Esmall","Fsmall",
    "Gsmall","Hsmall","Ismall","Jsmall","Ksmall",
    "Lsmall","Msmall","Nsmall","Osmall","Psmall",
    "Qsmall","Rsmall","Ssmall","Tsmall","Usmall",
    "Vsmall","Wsmall","Xsmall","Ysmall","Zsmall",
    // 300
    "colonmonetary","onefitted","rupiah","Tildesmall","exclamdownsmall",
    "centoldstyle","Lslashsmall","Scaronsmall","Zcaronsmall","Dieresissmall",
    "Brevesmall","Caronsmall","Dotaccentsmall","Macronsmall","figuredash",
    "hypheninferior","Ogoneksmall","Ringsmall","Cedillasmall","questiondownsmall",
    "oneeighth","threeeighths","fiveeighths","seveneighths","onethird",
    "twothirds","zerosuperior","foursuperior","fivesuperior","sixsuperior",
    "sevensuperior","eightsuperior","ninesuperior","zeroinferior","oneinferior",
    "twoinferior","threeinferior","fourinferior","fiveinferior","sixinferior",
    "seveninferior","eightinferior","nineinferior","centinferior","dollarinferior",
    "periodinferior","commainferior","Agravesmall","Aacutesmall","Acircumflexsmall",
    // 350
    "Atildesmall","Adieresissmall","Aringsmall","AEsmall","Ccedillasmall",
    "Egravesmall","Eacutesmall","Ecircumflexsmall","Edieresissmall","Igravesmall",
    "Iacutesmall","Icircumflexsmall","Idieresissmall","Ethsmall","Ntildesmall",
    "Ogravesmall","Oacutesmall","Ocircumflexsmall","Otildesmall","Odieresissmall",
    "OEsmall","Oslashsmall","Ugravesmall","Uacutesmall","Ucircumflexsmall",
    "Udieresissmall","Yacutesmall","Thornsmall","Ydieresissmall","001.000",
    "001.001","001.002","001.003","Black","Bold",
    "Book","Light","Medium","Regular","Roman",
    "Semibold"
  };

  final static String [] expertStrings = {
    ".notdef",".notdef",".notdef",".notdef",".notdef",
    ".notdef",".notdef",".notdef",".notdef",".notdef",
    ".notdef",".notdef",".notdef",".notdef",".notdef",
    ".notdef",".notdef",".notdef",".notdef",".notdef",
    ".notdef",".notdef",".notdef",".notdef",".notdef",
    ".notdef",".notdef",".notdef",".notdef",".notdef",
    ".notdef",".notdef","space","exclamsmall","Hungarumlautsmall",
    ".notdef","dollaroldstyle","dollarsuperior","ampersandsmall","Acutesmall",
    "parenleftsuperior","parenrightsuperior","twodotenleader","onedotenleader","comma",
    "hyphen","period","fraction","zerooldstyle","oneoldstyle",
    // 50
    "twooldstyle","threeoldstyle","fouroldstyle","fiveoldstyle","sixoldstyle",
    "sevenoldstyle","eightoldstyle","nineoldstyle","colon","semicolon",
    "commasuperior","threequartersemdash","periodsuperior","questionsmall",".notdef",
    "asuperior","bsuperior","centsuperior","dsuperior","esuperior",
    ".notdef",".notdef",".notdef","isuperior",".notdef",
    ".notdef","lsuperior","msuperior","nsuperior","osuperior",
    ".notdef",".notdef","rsuperior","ssuperior","tsuperior",
    ".notdef","ff","fi","fl","ffi",
    "ffl","parenleftinferior",".notdef","parenrightinferior","Circumflexsmall",
    "hyphensuperior","Gravesmall","Asmall","Bsmall","Csmall",
    // 100
    "Dsmall","Esmall","Fsmall","Gsmall","Hsmall",
    "Ismall","Jsmall","Ksmall","Lsmall","Msmall",
    "Nsmall","Osmall","Psmall","Qsmall","Rsmall",
    "Ssmall","Tsmall","Usmall","Vsmall","Wsmall",
    "Xsmall","Ysmall","Zsmall","colonmonetary","onefitted",
    "rupiah","Tildesmall",".notdef",".notdef",".notdef",
    ".notdef",".notdef",".notdef",".notdef",".notdef",
    ".notdef",".notdef",".notdef",".notdef",".notdef",
    ".notdef",".notdef",".notdef",".notdef",".notdef",
    ".notdef",".notdef",".notdef",".notdef",".notdef",
    // 150
    ".notdef",".notdef",".notdef",".notdef",".notdef",
    ".notdef",".notdef",".notdef",".notdef",".notdef",
    ".notdef","exclamdownsmall","centoldstyle","Lslashsmall",".notdef",
    ".notdef","Scaronsmall","Zcaronsmall","Dieresissmall","Brevesmall",
    "Caronsmall",".notdef","Dotaccentsmall",".notdef",".notdef",
    "Macronsmall",".notdef",".notdef","figuredash","hypheninferior",
    ".notdef",".notdef","Ogoneksmall","Ringsmall","Cedillasmall",
    ".notdef",".notdef",".notdef","onequarter","onehalf",
    "threequarters","questiondownsmall","oneeighth","threeeighths","fiveeighths",
    "seveneighths","onethird","twothirds",".notdef",".notdef",
    // 200
    "zerosuperior","onesuperior","twosuperior","threesuperior","foursuperior",
    "fivesuperior","sixsuperior","sevensuperior","eightsuperior","ninesuperior",
    "zeroinferior","oneinferior","twoinferior","threeinferior","fourinferior",
    "fiveinferior","sixinferior","seveninferior","eightinferior","nineinferior",
    "centinferior","dollarinferior","periodinferior","commainferior","Agravesmall",
    "Aacutesmall","Acircumflexsmall","Atildesmall","Adieresissmall","Aringsmall",
    "AEsmall","Ccedillasmall","Egravesmall","Eacutesmall","Ecircumflexsmall",
    "Edieresissmall","Igravesmall","Iacutesmall","Icircumflexsmall","Idieresissmall",
    "Ethsmall","Ntildesmall","Ogravesmall","Oacutesmall","Ocircumflexsmall",
    "Otildesmall","Odieresissmall","OEsmall","Oslashsmall","Ugravesmall",
    // 250
    "Uacutesmall","Ucircumflexsmall","Udieresissmall","Yacutesmall","Thornsmall",
    "Ydieresissmall"
  };

  final static String [] expertCharset = {
    ".notdef","space","exclamsmall","Hungarumlautsmall","dollaroldstyle",
    "dollarsuperior","ampersandsmall","Acutesmall","parenleftsuperior","parenrightsuperior",
    "twodotenleader","onedotenleader","comma","hyphen","period",
    "fraction","zerooldstyle","oneoldstyle","twooldstyle","threeoldstyle",
    "fouroldstyle","fiveoldstyle","sixoldstyle","sevenoldstyle","eightoldstyle",
    "nineoldstyle","colon","semicolon","commasuperior","threequartersemdash",
    "periodsuperior","questionsmall","asuperior","bsuperior","centsuperior",
    "dsuperior","esuperior","isuperior","lsuperior","msuperior",
    "nsuperior","osuperior","rsuperior","ssuperior","tsuperior",
    "ff","fi","fl","ffi","ffl",
    // 50
    "parenleftinferior","parenrightinferior","Circumflexsmall","hyphensuperior","Gravesmall",
    "Asmall","Bsmall","Csmall","Dsmall","Esmall",
    "Fsmall","Gsmall","Hsmall","Ismall","Jsmall",
    "Ksmall","Lsmall","Msmall","Nsmall","Osmall",
    "Psmall","Qsmall","Rsmall","Ssmall","Tsmall",
    "Usmall","Vsmall","Wsmall","Xsmall","Ysmall",
    "Zsmall","colonmonetary","onefitted","rupiah","Tildesmall",
    "exclamdownsmall","centoldstyle","Lslashsmall","Scaronsmall","Zcaronsmall",
    "Dieresissmall","Brevesmall","Caronsmall","Dotaccentsmall","Macronsmall",
    "figuredash","hypheninferior","Ogoneksmall","Ringsmall","Cedillasmall",
    // 100
    "onequarter","onehalf","threequarters","questiondownsmall","oneeighth",
    "threeeighths","fiveeighths","seveneighths","onethird","twothirds",
    "zerosuperior","onesuperior","twosuperior","threesuperior","foursuperior",
    "fivesuperior","sixsuperior","sevensuperior","eightsuperior","ninesuperior",
    "zeroinferior","oneinferior","twoinferior","threeinferior","fourinferior",
    "fiveinferior","sixinferior","seveninferior","eightinferior","nineinferior",
    "centinferior","dollarinferior","periodinferior","commainferior","Agravesmall",
    "Aacutesmall","Acircumflexsmall","Atildesmall","Adieresissmall","Aringsmall",
    "AEsmall","Ccedillasmall","Egravesmall","Eacutesmall","Ecircumflexsmall",
    "Edieresissmall","Igravesmall","Iacutesmall","Icircumflexsmall","Idieresissmall",
    // 150
    "Ethsmall","Ntildesmall","Ogravesmall","Oacutesmall","Ocircumflexsmall",
    "Otildesmall","Odieresissmall","OEsmall","Oslashsmall","Ugravesmall",
    "Uacutesmall","Ucircumflexsmall","Udieresissmall","Yacutesmall","Thornsmall",
    "Ydieresissmall"
  };

  final static String [] expertSubset = {
    ".notdef","space","dollaroldstyle","dollarsuperior","parenleftsuperior",
    "parenrightsuperior","twodotenleader","onedotenleader","comma","hyphen",
    "period","fraction","zerooldstyle","oneoldstyle","twooldstyle",
    "threeoldstyle","fouroldstyle","fiveoldstyle","sixoldstyle","sevenoldstyle",
    "eightoldstyle","nineoldstyle","colon","semicolon","commasuperior",
    "threequartersemdash","periodsuperior","asuperior","bsuperior","centsuperior",
    "dsuperior","esuperior","isuperior","lsuperior","msuperior",
    "nsuperior","osuperior","rsuperior","ssuperior","tsuperior",
    "ff","fi","fl","ffi","ffl",
    "parenleftinferior","parenrightinferior","hyphensuperior","colonmonetary","onefitted",
    // 50
    "rupiah","centoldstyle","figuredash","hypheninferior","onequarter",
    "onehalf","threequarters","oneeighth","threeeighths","fiveeighths",
    "seveneighths","onethird","twothirds","zerosuperior","onesuperior",
    "twosuperior","threesuperior","foursuperior","fivesuperior","sixsuperior",
    "sevensuperior","eightsuperior","ninesuperior","zeroinferior","oneinferior",
    "twoinferior","threeinferior","fourinferior","fiveinferior","sixinferior",
    "seveninferior","eightinferior","nineinferior","centinferior","dollarinferior",
    "periodinferior","commainferior"
  };
}