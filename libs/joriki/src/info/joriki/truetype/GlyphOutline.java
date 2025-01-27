/*
 * Copyright 2002 Felix Pahl. All rights reserved.
 * Use is subject to license terms.
 */

package info.joriki.truetype;

import info.joriki.io.Outputable;

abstract public class GlyphOutline implements Outputable
{
  public GlyphDescription glyphDescription;

  abstract public void interpret (ByteCodeInterpreter interpreter);
}
