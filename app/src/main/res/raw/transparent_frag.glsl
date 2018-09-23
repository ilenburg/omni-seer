precision highp float;
precision highp int;

varying vec2 texCoord;
uniform sampler2D tex;

void main() {
    vec4 baseColor = texture2D(tex, texCoord);
    if(baseColor.a > 0.0) {
        baseColor.a = 0.5;
    }
    gl_FragColor = baseColor;
}