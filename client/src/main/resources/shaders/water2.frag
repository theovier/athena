#ifdef GL_ES
precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoords;

uniform sampler2D u_texture;
uniform sampler2D u_textureDisplacement;
uniform float u_time;


void main() {
    vec2 displacement = texture2D (u_textureDisplacement, v_texCoords/6.0).xy;
    float t = v_texCoords.y + displacement.y * 0.1 - 0.15 + (sin (v_texCoords.x * 60.0+u_time) * 0.01);
    gl_FragColor = v_color * texture2D (u_texture, vec2 (v_texCoords.x, t));
}

