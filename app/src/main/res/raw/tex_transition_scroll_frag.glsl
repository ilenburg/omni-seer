precision highp float;
precision highp int;

varying vec2 texCoord;
uniform sampler2D tex;
uniform sampler2D tex2;
uniform float scroll;
uniform float time;

void main() {

    vec2 texPos = vec2(texCoord.x + fract(scroll), texCoord.y);

    float variation = abs(sin(time / 300.0)) + 0.5;

    variation = min(variation, 1.0);

    gl_FragColor = ((texture2D(tex, texPos)) * (1.0 - variation)) + (texture2D(tex2, texPos) * variation);
}