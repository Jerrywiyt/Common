### doc
https://projectlombok.org/features/
#### val
val map = new HashMap<String,String>();

eq

final Map<String,String> map = new HashMap<>();

#### var
var map = new HashMap<String,String>();

eq

Map<String,String> map = new HashMap<>();

#### @NonNull
this used for instead 'Preconditions.checkNotNull(param)' and 'Objects.requireNonNull(param)'

#### @Cleanup
This is used for auto clean up resource, such as Closeable resource.

@Cleanup InputStream inputStream;

eq

InputStream inputStream;
try{
    
}finally{
    inputStream.close();
}

#### @Getter and @Setter
This will generate get or set method for specified filed.

@Setter can work with @NonNull
#### @ToString
support callSuper param
#### @EqualsAndHashCode
support callSuper param
#### @NoArgsConstructor
#### @AllArgsConstructor
this can work with @NonNull, if a filed marked with @NonNull, null check will generate in constructor.
#### @RequiredArgsConstructor
only contains marked with @NonNull fields.
#### @Data
@Data is a convenient shortcut annotation that bundles the features of @ToString, @EqualsAndHashCode, @Getter / @Setter and @RequiredArgsConstructor together

#### @Value
This will generate immutable class.

What immutable?
- all field is set through constructor and only provide get method, when it is 
generated, it won't be change in its lifecycle.

#### @Builder

#### @SneakyThrows
if a Exception can be ignore, can use it.
