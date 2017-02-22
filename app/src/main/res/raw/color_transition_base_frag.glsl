varying vec2 texCoord;
uniform sampler2D tex;
uniform float time;

void main() {
    gl_FragColor = texture2D(tex, texCoord) * vec4(1.0f, cos(time)* 2.0f, 1.0f, 1.0f);
}