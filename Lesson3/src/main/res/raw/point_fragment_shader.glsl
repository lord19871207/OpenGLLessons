precision mediump float;
uniform sampler2D from, to;
uniform float progress;
varying vec2 vTextureCoord;
//final float res[] = {(float) gwidth, (float) gheight, 1.0f};
// default interpolationPower = 5;
uniform float interpolationPower;

void main() {
  vec4 fTex = texture2D(from,vTextureCoord);
  vec4 tTex = texture2D(to,vTextureCoord);
  gl_FragColor = mix(distance(fTex,tTex)>progress?fTex:tTex,
                     tTex,
                     pow(progress,interpolationPower));
}


//precision mediump float;
//// General parameters
//uniform sampler2D from;
//uniform sampler2D to;
//uniform float progress;
//varying vec2 vTextureCoord;
//
//const vec2 direction = vec2(1.0,-1.0);
//const float smoothness = 0.5;
//
//const vec2 center = vec2(0.5, 0.5);
//
//void main() {
//  vec2 v = normalize(direction);
//  v /= abs(v.x)+abs(v.y);
//  float d = v.x * center.x + v.y * center.y;
//  float m = smoothstep(-smoothness, 0.0, v.x * vTextureCoord.x + v.y * vTextureCoord.y - (d-0.5+progress*(1.+smoothness)));
//  gl_FragColor = mix(texture2D(to, vTextureCoord), texture2D(from, vTextureCoord), m);
//}

