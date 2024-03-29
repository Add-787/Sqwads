using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Sqwads.Application.Interfaces.Data;
using Sqwads.Infrastructure.Data;

namespace Sqwads.Infrastructure;

public static class ServiceCollectionExtensions
{ 
    public static IServiceCollection AddInfrastructure(this IServiceCollection services, IConfiguration configuration)
    {
        services.AddDbContext<AppDbContext>(opts => opts.UseSqlServer(configuration.GetConnectionString("DbConnection")));
        services.AddScoped<IUnitOfWork, UnitOfWork>();
        return services;
    }

}
