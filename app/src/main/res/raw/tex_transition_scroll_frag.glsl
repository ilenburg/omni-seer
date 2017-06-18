varying vec2 texCoord;
uniform sampler2D tex;
uniform sampler2D tex2;
uniform float scroll;
uniform float time;

void main() {

    vec2 texPos = vec2(texCoord.x + scroll, texCoord.y);

    float variation = abs(sin(time / 100.0f) * 3.0f);

    if(variation > 1.0f) {
        if (variation < 2.0f) {
            variation = variation - 1.0f;
        }
        else {
            variation = 1.0f;
        }
    }
    else {
        variation = 0.0f;
    }
    gl_FragColor = ((texture2D(tex, texPos)) * (1.0f - variation)) + (texture2D(tex2, texPos) * variation);
}