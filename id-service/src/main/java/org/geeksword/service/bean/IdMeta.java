package org.geeksword.service.bean;

import lombok.AllArgsConstructor;

import java.io.Serializable;


/**
 * 最大峰值型
 * ------------------------------------------------------
 * |字段 | 版本 | 类型 | 生成方式 | 秒级时间 | 序列号 | 机器ID |
 * ------------------------------------------------------
 * |位数 | 63   | 62  | 60-61   | 30-59   |10-29  | 0-9   |
 * -------------------------------------------------------
 * 最小粒度型
 * --------------------------------------------------------
 * | 字段 | 版本 | 类型 | 生成方式 | 秒级时间 | 序列号 | 机器ID |
 * --------------------------------------------------------
 * | 位数 | 63  | 62 | 60-61    | 30-59    | 10-29 | 0-9   |
 * ---------------------------------------------------------
 */

@AllArgsConstructor
public class IdMeta implements Serializable {
    private static final long serialVersionUID = 7641163669231786504L;

    /**
     * 机器id 0~9
     */
    private byte machineBits;

    /**
     * 序列号 10~29
     * 毫秒下 10~19
     */
    private byte seqBits;

    /**
     * 秒级时间 30~59
     * 毫秒级时间 20~59
     */
    private byte timeBits;

    /**
     * 生成方式 60~61
     */
    private byte genMethodBits;

    /**
     * 类型 62位
     */
    private byte typeBits;

    /**
     * 版本 63位
     */
    private byte versionBits;


    public byte getMachineBits() {
        return machineBits;
    }

    public void setMachineBits(byte machineBits) {
        this.machineBits = machineBits;
    }

    public long genMachineBitsMask() {
        return ~(-1L << machineBits);
    }

    public byte getSeqBits() {
        return seqBits;
    }

    public void setSeqBits(byte seqBits) {
        this.seqBits = seqBits;
    }

    public long getSeqBitsStartPos() {
        return machineBits;
    }

    public long getSeqBitsMask() {
        return ~(-1L << seqBits);
    }

    public void setTimeBits(byte timeBits) {
        this.timeBits = timeBits;
    }

    public byte getTimeBits() {
        return timeBits;
    }

    public long getTimeBitsStartPos() {
        return machineBits + seqBits;
    }

    public long getTimeBitsMask() {
        return ~(-1L << timeBits);
    }

    public byte getGenMethodBits() {
        return genMethodBits;
    }

    public void setGenMethodBits(byte genMethodBits) {
        this.genMethodBits = genMethodBits;
    }

    public long getGenMethodBitsStartPos() {
        return machineBits + seqBits + timeBits;
    }

    public long getGenMethodBitsMask() {
        return ~(-1L << genMethodBits);
    }

    public byte getTypeBits() {
        return typeBits;
    }

    public void setTypeBits(byte typeBits) {
        this.typeBits = typeBits;
    }

    public long getTypeBitsStartPos() {
        return machineBits + seqBits + timeBits + genMethodBits;
    }

    public long getTypeBitsMask() {
        return ~(-1L << typeBits);
    }

    public byte getVersionBits() {
        return versionBits;
    }

    public void setVersionBits(byte versionBits) {
        this.versionBits = versionBits;
    }

    public long getVersionBitsStartPos() {
        return machineBits + seqBits + timeBits + genMethodBits + typeBits;
    }

    public long getVersionBitsMask() {
        return ~(-1L << versionBits);
    }
}
