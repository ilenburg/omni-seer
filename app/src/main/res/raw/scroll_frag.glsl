varying vec2 texCoord;
uniform sampler2D tex;
uniform float scroll;

void main() {
    gl_FragColor = texture2D(tex, vec2(texCoord.x + fract(scroll), texCoord.y));
}