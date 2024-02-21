using Sqwads.Application;
using Sqwads.Infrastructure;
using Sqwads.Infrastructure.Data;

var builder = WebApplication.CreateBuilder(args);

// Database
var dbConnectionString = builder.Configuration.GetConnectionString("SqwadsDb") ?? throw new InvalidOperationException("Missing connection string configuration");
builder.Services.AddSqlServer<ApplicationDbContext>(dbConnectionString);

// Add services to the container.
var serviceCollection = builder.Services;
serviceCollection.AddApplication();
serviceCollection.AddInfrastructure(builder.Configuration);

builder.Services.AddControllers();
builder.Services.AddApiVersioning(options =>
{
    options.DefaultApiVersion = new Microsoft.AspNetCore.Mvc.ApiVersion(1, 0);
    options.AssumeDefaultVersionWhenUnspecified = true;
    options.ReportApiVersions = true;
});
// Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

var app = builder.Build();

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

// app.UseHttpsRedirection();

app.UseAuthorization();

app.MapControllers();

app.Run();
