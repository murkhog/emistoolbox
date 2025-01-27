package com.emistoolbox.common.model;

import com.emistoolbox.common.model.meta.EmisMetaEnum;

public abstract interface EmisEnumValue
{
    public abstract EmisMetaEnum getEnum();

    public abstract void setEnum(EmisMetaEnum paramEmisMetaEnum);

    public abstract String getValue();

    public abstract void setValue(String paramString);

    public abstract byte getIndex();

    public abstract void setIndex(byte paramByte);
}
