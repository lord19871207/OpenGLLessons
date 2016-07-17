precision mediump float;
uniform sampler2D from,to;
uniform float progress;
varying vec2 vTextureCoord;

vec2 offset(float progress, float x, float theta) {
  float phase = progress*progress + progress + theta;
  float shifty = 0.03*progress*cos(10.0*(progress+x));
  return vec2(0, shifty);
}

void main(){
 gl_FragColor = mix(texture2D(from,vTextureCoord+offset(progress,vTextureCoord.x,0.0)),texture2D(to,vTextureCoord+offset(1.0-progress,vTextureCoord.x,3.14)),progress);
}