varying vec2 texCoord;
uniform sampler2D tex;
uniform sampler2D tex2;
uniform float scroll;
uniform float time;

void main() {
    vec2 texPos = vec2(texCoord.x + scroll, texCoord.y);
    float parsedTime;
    if(time > 1.0f) {
        if (time < 2.0f) {
            parsedTime = time - 1.0f;
        }
        else {
            parsedTime = 1.0f;
        }
    }
    else {
        parsedTime = 0.0f;
    }
    gl_FragColor = ((texture2D(tex, texPos)) * (1.0f - parsedTime)) + (texture2D(tex2, texPos) * parsedTime);
}