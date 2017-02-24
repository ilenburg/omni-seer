varying vec2 texCoord;
uniform sampler2D tex;
uniform float time;

void main() {
    vec4 baseColor = texture2D(tex, texCoord);
    float totalColor = baseColor.r + baseColor.g + baseColor.b;
    vec4 targetColor = (baseColor + vec4(totalColor - baseColor.r, totalColor - baseColor.g, totalColor - baseColor.b, 0.0f)) / 2.0f;
    gl_FragColor = baseColor * (1.0f - time) + targetColor * time;
}