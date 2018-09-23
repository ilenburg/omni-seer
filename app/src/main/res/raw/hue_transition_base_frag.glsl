precision highp float;
precision highp int;

varying vec2 texCoord;
uniform sampler2D tex;
uniform float time;

void main() {
    float variation = sin(time) * 0.25f;
    gl_FragColor = texture2D(tex, texCoord) * vec4(0.0, 0.75 + variation, 1.0, 1.0);
}