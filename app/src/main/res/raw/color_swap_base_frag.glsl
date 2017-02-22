varying vec2 texCoord;
uniform sampler2D tex;

void main() {
    vec4 color = texture2D(tex, texCoord);
    float totalColor = color.r + color.g + color.b;
    gl_FragColor = (color + vec4(totalColor - color.r, totalColor - color.g, totalColor - color.b, 0.0f)) / 2.0f;
}