attribute vec2 a_Position;
void main() {
    gl_Position = vec4(a_Position,0.0,1.0);
    gl_PointSize = 50.0;
}
