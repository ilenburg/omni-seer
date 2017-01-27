varying vec2 texCoord;

uniform sampler2D tex;

void main() {
    gl_FragColor = texture2D(tex, texCoord);
    //gl_FragColor = vec4(1.0f, 0.0f, 0.0f, 1.0f);
}