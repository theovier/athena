attribute vec4 a_position;
attribute vec4 a_color;
attribute vec2 a_texCoord0;
attribute vec2 a_texCoordNoise;

uniform mat4 u_projTrans;
uniform sampler2D u_texture;
uniform sampler2D u_noiseTexture;

varying vec4 v_color;
varying vec2 v_texCoords;
varying vec2 v_texCoordsNoise;

void main() {
    v_color = a_color;
    v_texCoords = a_texCoord0;
    v_texCoordsNoise = texture2D(u_noiseTexture, a_texCoord0); //retrieves texel, i.e. color, for a texture at given coordinates
    gl_Position = u_projTrans * a_position;
}