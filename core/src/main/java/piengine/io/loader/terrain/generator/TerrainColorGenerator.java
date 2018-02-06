package piengine.io.loader.terrain.generator;

import piengine.core.base.type.color.Color;
import piengine.core.utils.ColorUtils;
import piengine.core.utils.MathUtils;

public class TerrainColorGenerator {

    private final Color[] boimeColors;
    private final float spread;
    private final float halfSpread;
    private final float part;

    public TerrainColorGenerator(final Color[] boimeColors, final float spread) {
        this.boimeColors = boimeColors;
        this.spread = spread;
        this.halfSpread = spread / 2f;
        this.part = 1f / (boimeColors.length - 1);
    }

    public Color generate(final float height) {
        float value = (height + 1) / 2f;
        value = MathUtils.clamp((value - halfSpread) * (1f / spread), 0f, 0.9999f);
        int firstBiome = (int) Math.floor(value / part);
        float blend = (value - (firstBiome * part)) / part;

        return ColorUtils.interpolateColors(boimeColors[firstBiome], boimeColors[firstBiome + 1], blend);
    }
}
