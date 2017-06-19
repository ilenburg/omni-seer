varying vec2 texCoord;
uniform sampler2D tex;
uniform float time;

void main() {
    float variation = sin(time) * 0.25f;
    gl_FragColor = texture2D(tex, texCoord) * vec4(0.0f, 0.75f + variation, 1.0f, 1.0f);
}