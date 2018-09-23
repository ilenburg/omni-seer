precision highp float;
precision highp int;

varying vec2 texCoord;
uniform sampler2D tex;
uniform float damage;

void main() {
    gl_FragColor = texture2D(tex, texCoord) + vec4(damage, 0, 0, 0);
}