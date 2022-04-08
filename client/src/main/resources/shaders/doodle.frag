#ifdef GL_ES
    precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoords;

uniform sampler2D u_texture;
uniform sampler2D u_textureDisplacement;
uniform float u_time;



float random(vec2 co) {
    return fract(sin(dot(co, vec2(12.9898, 78.233))) * 43758.5453);
}

float noise(vec2 seed) {
    vec2 i = floor(seed);
    vec2 f = fract(seed);

    float a = random(i);
    float b = random(i + vec2(1.0, 0.0));
    float c = random(i + vec2(0.0, 1.0));
    float d = random(i + vec2(1.0, 1.0));

    vec2 u = f * f * (3.0 - 2.0 * f);

    return mix(a, b, u.x) + (c - a) * u.y * (1.0 - u.x) + (d - b) * u.x * u.y;
}

vec2 doodleTextureOffset(vec2 textureCoords, vec2 maxOffset, float time, float frameTime, int frameCount, vec2 noiseScale) {
    float timeValue = mod(floor(time / frameTime), frameCount) + 1;
    vec2 offset = vec2(0.0, 0.0);
    vec2 coordsPlusTime = (textureCoords + timeValue);
    offset.x = (noise(coordsPlusTime * noiseScale.x) * 2.0 - 1.0) * maxOffset.x;
    offset.y = (noise(coordsPlusTime * noiseScale.y) * 2.0 - 1.0) * maxOffset.y;
    return offset;
}

void main() {
    vec2 offset = doodleTextureOffset(v_texCoords, vec2(0.01, 0.01), u_time, 0.2, 24, vec2(20, 20));
    gl_FragColor = v_color * texture2D(u_texture, v_texCoords + offset);
}

