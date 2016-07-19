precision highp float;
#endif
uniform sampler2D from, to;
uniform float progress;
varying vec2 vTextureCoord;
// Slide Down: translateX = 0, translateY = -1
// Slide Left: translateX = -1, translateY = 0
// Slide Right: translateX = 1, translateY = 0
// Slide Up: translateX = 0, translateY = 1
uniform float translateX;
uniform float translateY;

void main() {
    float x = progress * translateX;
    float y = progress * translateY;

    if (x >= 0.0 && y >= 0.0) {
        if (vTextureCoord.x >= x && vTextureCoord.y >= y) {
            gl_FragColor = texture2D(from, vTextureCoord - vec2(x, y));
        }
        else {
            vec2 uv;
            if (x > 0.0)
                uv = vec2(x - 1.0, y);
            else if (y > 0.0)
                uv = vec2(x, y - 1.0);
            gl_FragColor = texture2D(to, vTextureCoord - uv);
        }
    }
    else if (x <= 0.0 && y <= 0.0) {
        if (vTextureCoord.x <= (1.0 + x) && vTextureCoord.y <= (1.0 + y))
            gl_FragColor = texture2D(from, vTextureCoord - vec2(x, y));
        else {
            vec2 uv;
            if (x < 0.0)
                uv = vec2(x + 1.0, y);
            else if (y < 0.0)
                uv = vec2(x, y + 1.0);
            gl_FragColor = texture2D(to, vTextureCoord - uv);
        }
    }
    else
        gl_FragColor = vec4(0.0);
}