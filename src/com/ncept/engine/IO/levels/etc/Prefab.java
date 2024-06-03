
package com.ncept.engine.IO.levels.etc;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 *
 * @author Douglas Rocha de Oliveira
 */
@XStreamAlias("prefab")
public class Prefab {
    public String prefabName;
    public int x, y;
    public int zIndex;
}
