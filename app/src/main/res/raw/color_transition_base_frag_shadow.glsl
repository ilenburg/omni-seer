precision highp float;
precision highp int;

varying vec2 texCoord;
uniform sampler2D tex;
uniform float time;

void main() {
    float variation = abs(sin(time));
    gl_FragColor = texture2D(tex, texCoord) * vec4(1.0, 0.5 + variation, 0.5 + variation, 1.0);
}