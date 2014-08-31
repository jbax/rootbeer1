
__device__ int
org_trifort_classConstant(int type_num){
  int * temp = (int *) m_Local[2];   
  return temp[type_num];
}

__device__  char *
org_trifort_gc_deref(int handle){

  char * data_arr = (char * ) m_Local[0];
  long long lhandle = handle;
  lhandle = lhandle << 4;
  return &data_arr[lhandle];
}

__device__ int
org_trifort_gc_malloc(int size){
  int space_size = (int) m_Local[1];
  int ret = org_trifort_gc_malloc_no_fail(size);
  int end = ret + ((size + 16) >> 4);
  if(end >= space_size){
    return -1;
  }
  return ret;
}

//TODO: don't pass gc_info everywhere because free pointer is __device__
__device__ int * global_free_pointer; 

__device__ int
org_trifort_gc_malloc_no_fail(int size){
  if(size % 16 != 0){
    size += (16 - (size % 16));
  }
  size >>= 4;

  int ret;
  ret = atomicAdd(global_free_pointer, size);
  return ret;
}

__device__
long long java_lang_System_nanoTime(int * exception){
  return (long long) clock64();
}

__global__ void entry(char * objectMemory, int * exceptions, 
  int * classRefs, int spaceSize, int numBlocks, int handle){

  int loop_control = blockIdx.x * blockDim.x + threadIdx.x;
  
  if(threadIdx.x == 0){
    m_Local[0] = (size_t) objectMemory;
    m_Local[1] = (size_t) spaceSize;
    m_Local[2] = (size_t) classRefs;
  }
  __syncthreads();
      
  if(loop_control >= numBlocks){
    return;
  } else {
    int exception = 0; 
    %%invoke_run%%(handle, &exception);  
    if(%%using_exceptions%%){
      exceptions[loop_control] = exception;
    }
  }
}
