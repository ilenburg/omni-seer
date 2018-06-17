varying vec2 texCoord;
uniform sampler2D tex;
uniform float time;

void main() {
    float variation = abs(sin(time));
    gl_FragColor = texture2D(tex, texCoord) * vec4(1.0f, 0.5f + variation, 0.5f + variation, 1.0f);
}