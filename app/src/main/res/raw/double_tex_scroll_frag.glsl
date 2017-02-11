varying vec2 texCoord;
uniform sampler2D tex;
uniform sampler2D tex2;
uniform float scroll;
uniform float time;

void main() {
    vec2 texPos = vec2(texCoord.x + scroll, texCoord.y);
    gl_FragColor = ((texture2D(tex, texPos)) * (1.0f - time)) + (texture2D(tex2, texPos) * time);
    //(texture2D(tex[1], texPos) * (1.0f - time)) + (texture2D(tex[0], texPos) * time);
    //texture2D(tex2, texPos);
    //vec4(0.0f, 1.0f, 1.0f, 1.0f);
    //(texture2D(tex, texPos) * (1.0f - time)) + (texture2D(tex2, texPos) * time);
}