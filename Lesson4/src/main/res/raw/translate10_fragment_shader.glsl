precision mediump float;
uniform sampler2D from, to;
uniform float progress;
varying vec2 vTextureCoord;

void main() {

    if (progress >= 0.0) {
        if (vTextureCoord.y >= progress) {
            gl_FragColor = texture2D(from, vTextureCoord - vec2(0.0, progress));
        }
        else {
            vec2 uv;
            if (progress > 0.0){
                uv = vec2(0.0, progress - 1.0);
            }
            gl_FragColor = texture2D(to, vTextureCoord - uv);
        }
    }
    else if (progress <= 0.0) {
        if (vTextureCoord.y <= (1.0 + progress))
            gl_FragColor = texture2D(from, vTextureCoord - vec2(0.0, progress));
        else {
            vec2 uv;
            if (progress < 0.0)
                uv = vec2(0.0, progress + 1.0);
            gl_FragColor = texture2D(to, vTextureCoord - uv);
        }
    }
    else
        gl_FragColor = vec4(0.0);
}